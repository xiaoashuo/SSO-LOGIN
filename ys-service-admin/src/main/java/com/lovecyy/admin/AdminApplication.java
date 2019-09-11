package com.lovecyy.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ys
 * @topic
 * @date 2019/9/10 16:34
 */
@SpringBootApplication(scanBasePackages = "com.lovecyy")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
