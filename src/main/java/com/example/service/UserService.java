package com.example.service;

import com.example.dao.user.UserDao;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserService {
    @Autowired
    private UserDao userDao;

    public User queryUser(String username,String password){
        return userDao.queryUser(username,password);
    }
}
