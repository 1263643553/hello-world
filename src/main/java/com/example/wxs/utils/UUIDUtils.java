package com.example.wxs.utils;

import java.util.UUID;

public class UUIDUtils {

    public String toUUID(String preString){
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
