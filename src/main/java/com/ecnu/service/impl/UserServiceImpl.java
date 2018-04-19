package com.ecnu.service.impl;

import com.ecnu.dao.UserDao;
import com.ecnu.entity.User;
import com.ecnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author asus
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUsers() {
        return userDao.getAllUser();
    }

    @Override
    public List<User> queryUsers(User user) {
        return userDao.queryUsers(user);
    }

    @Override
    public User queryUserById(int id) {
        return userDao.findUserById(id);
    }

    @Override
    public User queryUserByName(String name) {
        User loginUser = userDao.findUserByName(name);
        return loginUser;
    }


    @Override
    public Boolean addUser(User usr) {
        String name = usr.getUserName();
        User queryUser = userDao.findUserByName(name);
        if (queryUser != null) {
            return false;
        }
        userDao.insertUser(usr);
        if (usr.getId() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteUser(int id) {
        User user = new User();
        user.setId(id);
        int res = userDao.deleteUser(user);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean changeAuth(int id, int auth) {
        User user = new User();
        user.setId(id);
        user.setAuth(auth);
        int res = userDao.updateUser(user);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean changePwd(int id, String pwd) {
        User user = new User();
        user.setId(id);
        user.setPwd(pwd);
        int res = userDao.updateUser(user);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateUser(User user) {
        int res = userDao.updateUser(user);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }
}
