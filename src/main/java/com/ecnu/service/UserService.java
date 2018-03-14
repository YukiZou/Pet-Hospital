package com.ecnu.service;

import com.ecnu.entity.User;

import java.util.List;

public interface UserService {

    //test
    List<User> getUsers();

    /**
     * 新增用户
     * @param usr
     */
    void addUser(User usr);
}
