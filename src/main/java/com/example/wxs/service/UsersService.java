package com.example.wxs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wxs.entity.Users;
import org.springframework.http.ResponseEntity;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UsersService {
    public List<Users> usersList();
    public IPage<Users> getAllUsers(Page<Users> page);
    public Integer insertOne(Users users);
    public List<Users> getUserIdByUsernameAndPassword(String userName);
    public String getWxCode();
    public String getWxSessionKeyAndOpenId(String appId,String appSecret,String code) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException;
}
