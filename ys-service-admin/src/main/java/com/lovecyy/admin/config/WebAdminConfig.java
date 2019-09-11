package com.lovecyy.admin.config;

import com.lovecyy.admin.interceptor.WebAdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ys
 * @topic
 * @date 2019/9/10 16:57
 */
@Configuration
public class WebAdminConfig implements WebMvcConfigurer {

    @Bean
    public WebAdminInterceptor webAdminInterceptor(){
        return new WebAdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webAdminInterceptor()).addPathPatterns("/**");
    }
}
