package com.example.wxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.wxs.mapper")
@MapperScan("com.example.wxs.aoplog.mapper")
public class WxsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxsApplication.class, args);
    }

}
