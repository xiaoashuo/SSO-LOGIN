package com.lovecyy.admin.interceptor;

import com.lovecyy.sso.common.dto.HpUser;
import com.lovecyy.sso.common.utils.CookieUtils;
import com.lovecyy.sso.common.utils.HttpServletUtils;
import com.lovecyy.sso.common.utils.MapperUtils;
import com.lovecyy.sso.service.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ys
 * @topic
 * @date 2019/9/10 16:58
 */

public class WebAdminInterceptor implements HandlerInterceptor {

    @Value("${hosts.sso}")
    private String sso;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, "token");
        //token为空一定没定路
        if (StringUtils.isBlank(token)){
           // response.sendRedirect("http://localhost:8080/login?url=http://localhost:6060/index");
            response.sendRedirect(String.format("%s/login?url=%s",sso, HttpServletUtils.getFullPath(request)));
             return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //从局部会话获取
        HpUser hpUser = (HpUser) request.getSession().getAttribute("hpUser");
        //若局部会话不为空说明已登录
        if (hpUser!=null){
            if (modelAndView!=null){
                modelAndView.addObject("hpUser",hpUser);
            }
        }
        else{
            //从cookie获取cookie 此时还要判断token是否为空 因为可能碰到临界点状态
            String token = CookieUtils.getCookieValue(request, "token");
            if (StringUtils.isNotBlank(token)){
                //获取登录用户
                String loginCode = null;
                try {
                    Object o = redisService.get(token);
                    if (o!=null){
                        loginCode=String.valueOf(o);
                    }

                } catch (Exception e) {
                    //e.printStackTrace();
                }
                if (StringUtils.isNotBlank(loginCode)) {
                    //获取登录用户信息
                    String json = (String) redisService.get(loginCode);
                    if (StringUtils.isNotBlank(json)){
                          hpUser = MapperUtils.json2pojo(json, HpUser.class);
                            //已登录状态了 然后创建局部会话
                            if (modelAndView!=null){
                                modelAndView.addObject("hpUser",hpUser);
                            }
                            request.getSession().setAttribute("hpUser",hpUser);

                    }

                }
            }
        }

        //二次确认是否有用户信息
        if (hpUser==null){
            response.sendRedirect("http://localhost:8080/login?url=http://localhost:6060/index");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
