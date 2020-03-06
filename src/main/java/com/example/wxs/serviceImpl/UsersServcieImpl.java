package com.example.wxs.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wxs.configs.Log;
import com.example.wxs.dao.UsersDao;
import com.example.wxs.entity.Users;
import com.example.wxs.mapper.UsersMapper;
import com.example.wxs.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServcieImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;
    @Resource
    private UsersMapper usersMapper;

    @Override
    public List<Users> usersList() {
        List<Users> allUsers=new ArrayList<>();
        Users users=new Users();
        users.setUserName("ceshi");
        users.setPassword("test");
        usersDao.save(users);
        allUsers.add(users);
        System.out.println(allUsers.isEmpty());
        return allUsers;
    }

    @Override
    public IPage<Users> getAllUsers(Page<Users> page) {
        return usersMapper.selectPage(page,null);
    }

    @Override
    @Log("插入人员")
    public Integer insertOne(Users users) {
        return usersMapper.insert(users);
    }

    @Override
    public List<Users> getUserIdByUsernameAndPassword(String userName) {
        QueryWrapper<Users> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.like("user_name",userName);
        return usersMapper.selectList(queryWrapper1);
    }


}
