package com.aperise;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //这两个不能因为第一个存在而省略第二个,他们影响的范围不同,"/user*"无法匹配"/user/*"的模式
//        registry.addMapping("/user*");
        registry.addMapping("/user/*");
    }
}
