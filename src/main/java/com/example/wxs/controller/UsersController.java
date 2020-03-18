package com.example.wxs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wxs.aoplog.entity.SysLog;
import com.example.wxs.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.wxs.serviceImpl.UsersServcieImpl;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersServcieImpl usersServcieImpl;

    @RequestMapping("/test")
    public void test(){
        usersServcieImpl.usersList();
    }

    @GetMapping("/getAll")
    public IPage<Users> findByPage(@RequestParam(value = "pageNo",required = false) int pageNo, @RequestParam(value = "pageSize",required = false) int pageSize){
        Page<Users> page=new Page<>(pageNo,pageSize);
        return usersServcieImpl.getAllUsers(page);
    }

    @GetMapping("/getUserByUsername")
    public List<Users> getUserIdByUsernameAndPassword(@RequestParam String userName){
        return usersServcieImpl.getUserIdByUsernameAndPassword(userName);
    }

    @PostMapping("/insertOne")
    public Integer insertUsers(@RequestBody Users users){
        return usersServcieImpl.insertOne(users);
    }

    @RequestMapping("/getWxInfo")
    @ResponseBody
    public String getWxInfo(String appId,String appSecret,String code) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return usersServcieImpl.getWxSessionKeyAndOpenId(appId,appSecret,code);
    }

}
