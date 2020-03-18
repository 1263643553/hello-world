package com.example.wxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.example.wxs.mapper")
@MapperScan("com.example.wxs.aoplog.mapper")
@MapperScan("com.example.wxs.wxsession.mapper")
@EnableCaching
public class WxsApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WxsApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WxsApplication.class);
    }

}
