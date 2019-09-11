package com.lovecyy.sso.service.impl;

import com.lovecyy.sso.common.dto.HpUser;
import com.lovecyy.sso.common.mapper.HpUserMapper;
import com.lovecyy.sso.common.service.HpUserService;
import com.lovecyy.sso.common.utils.MapperUtils;
import com.lovecyy.sso.service.LoginService;
import com.lovecyy.sso.service.redis.service.RedisService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;
import tk.mybatis.mapper.entity.Example;

/**
 * @author ys
 * @topic
 * @date 2019/9/10 12:49
 */
@Service
public class LoginServiceImpl implements LoginService {

    private Logger log= LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private HpUserMapper hpUserMapper;

    /**
     * 用户登录功能
     * @param loginCode
     * @param plantPassword
     * @return
     */
    @Override
    public HpUser login(String loginCode, String plantPassword) {
        HpUser hpUser=null;
        //先从缓存中取 如果缓存中存在则 说明登录过
        String  json = (String) redisService.get(loginCode);
        //说明缓存中没有数据 需要去数据库查找
        if (json==null){
            Example example = new Example(HpUser.class);
            example.createCriteria().andEqualTo("mobileNo",loginCode);
            hpUser = hpUserMapper.selectOneByExample(example);
            if (hpUser==null){return  null;}

            BASE64Encoder base64Encoder = new BASE64Encoder();
            String encode = base64Encoder.encode((plantPassword + hpUser.getSalt()).getBytes());
            String newPwd = DigestUtils.md5Hex(encode);
            //用户登录成功
            if (hpUser.getPassword().equals(newPwd)){
                try {
                    redisService.set(loginCode, MapperUtils.obj2json(hpUser),24*60*60);
                } catch (Exception e) {
                  //  e.printStackTrace();
                }
                return hpUser;
            }else{
                return null;
            }
        }
        else{
            try {
                hpUser= MapperUtils.json2pojo(json,HpUser.class);
            } catch (Exception e) {
                //e.printStackTrace();
                log.warn(e.getMessage());
            }
        }
        return hpUser;
    }
}
