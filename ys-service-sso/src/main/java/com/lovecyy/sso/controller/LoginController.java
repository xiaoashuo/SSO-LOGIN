package com.lovecyy.sso.controller;

import com.lovecyy.sso.common.dto.HpUser;
import com.lovecyy.sso.common.utils.CookieUtils;
import com.lovecyy.sso.common.utils.MapperUtils;
import com.lovecyy.sso.service.LoginService;
import com.lovecyy.sso.service.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author ys
 * @topic
 * @date 2019/9/10 14:22
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String url,HttpServletRequest request,Model model){
        String token = CookieUtils.getCookieValue(request, "token");
        //如果token不为空 说明可能已登录  如果token为空说明一定未登录
        if (StringUtils.isNotBlank(token)){
            String loginCode = (String) redisService.get(token);
            //若登录码 即用户登录账号
            if (StringUtils.isNotBlank(loginCode)){
                //用户登录信息json
                String json = (String) redisService.get(loginCode);
                if (StringUtils.isNotBlank(json)){
                    try {
                        HpUser hpUser = MapperUtils.json2pojo(json, HpUser.class);
                        //说明已登陆 用户信息不为空
                        if (hpUser!=null){
                            //判断从哪个地方过来的 然后踢回去
                            if (StringUtils.isNotBlank(url)){
                                return "redirect:"+url;
                            }
                            //只用用户不为空 才放信息
                            //因为有可能出现临界情况 post时信息还在 到get时信息过期了
                            //所以要判断档用户存在时 才放入 否则就停留在登录界面
                            model.addAttribute("hpUser",hpUser);
                        }

                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(url)){
            model.addAttribute("url",url);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String loginCode,@RequestParam String password,
                        @RequestParam(required = false) String url,
                        HttpServletRequest request, HttpServletResponse response,
                        RedirectAttributes redirectAttributes){
        HpUser hpUser = loginService.login(loginCode, password);
        //登录失败 用户不存在
        if (hpUser==null){
          redirectAttributes.addFlashAttribute("message","用户名或密码错误");
        }
        //登录成功 生成token存入redis 并写入token
        else{
            String token= UUID.randomUUID().toString().replace("-","");
            boolean result = redisService.set(token, hpUser.getMobileNo(), 24 * 60 * 60);
            //若放入redis成功 然后写入cookies
            if (result){
                CookieUtils.setCookie(request,response,"token",token,24*60*60);
                //如果传入url不为空 则跳转至 进入url
                if (StringUtils.isNotBlank(url)){
                    return "redirect:"+url;
                }
            }
            //放入redis失败 所以要回到登录页
            else{
               redirectAttributes.addFlashAttribute("message","服务器异常,请稍后在试!");
            }
        }
        //System.out.println(hpUser);
        return "redirect:/login";
    }
    /**
     * 注销
     * @param request
     * @param response
     * @return
     */
    @GetMapping("logout")
    public String logout(@RequestParam(required = false) String url,Model model,HttpServletRequest request,HttpServletResponse response){
        CookieUtils.deleteCookie(request,response,"token");
        return login(url,request,model);
    };
}
