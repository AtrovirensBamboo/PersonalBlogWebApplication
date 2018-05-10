package com.example.dao.user;

import org.apache.ibatis.annotations.Param;

public interface UserDao {
    com.example.model.User queryUser(@Param("username")String username,@Param("password") String password);
}
