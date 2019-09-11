package com.lovecyy.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ys
 * @topic
 * @date 2019/9/10 11:49
 */
@SpringBootApplication(scanBasePackages = "com.lovecyy")
@MapperScan(basePackages = "com.lovecyy.sso.common.mapper")
public class SSOApplication {

    public static void main(String[] args) {
        SpringApplication.run(SSOApplication.class,args);
    }
}
