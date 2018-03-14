package com.ecnu.service.impl;

import com.ecnu.dao.UserDao;
import com.ecnu.entity.User;
import com.ecnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUsers() {
        return userDao.getAllUser();
    }

    @Override
    public void addUser(User usr) {
        userDao.insertUser(usr);
    }
}
