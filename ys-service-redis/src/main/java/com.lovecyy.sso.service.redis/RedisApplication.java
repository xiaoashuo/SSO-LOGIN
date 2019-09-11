package com.lovecyy.sso.service.redis;

import com.lovecyy.sso.common.dto.TbUser;
import com.lovecyy.sso.service.redis.service.RedisService;
import com.lovecyy.sso.service.redis.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ys
 * @topic
 * @date 2019/9/9 17:08
 */
@SpringBootApplication
public class RedisApplication  {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class,args);
    }

    @Autowired
    RedisServiceImpl redisService;


}
