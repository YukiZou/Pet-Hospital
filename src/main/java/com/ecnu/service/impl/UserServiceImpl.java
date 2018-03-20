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
    public List<User> queryUsers(User user) {
        return userDao.queryUsers(user);
    }

    /**
     *user login
     * @param usr
     * @return if null,登录不成功。
     */
    @Override
    public User checkLogin(User usr) {
        String userName = usr.getUserName();
        String pwd = usr.getPwd();
        if(userName != null && !userName.equals("")) {
            User loginUser = userDao.findUserByName(userName);
            if (loginUser != null && loginUser.getPwd().equals(pwd)) {
                return loginUser;
            }
        }
        return null;
    }

    @Override
    public void addUser(User usr) {
        userDao.insertUser(usr);
    }

    @Override
    public void deleteUser(int id) {
        User user = new User();
        if (id > 0) {
            user.setId(id);
            userDao.deleteUser(user);
        }
    }

    @Override
    public void changeAuth(int id, int auth) {
        User user = new User();
        if (id > 0 && auth > 0 && auth < 4) {
            user.setId(id);
            user.setAuth(auth);
            userDao.updateUser(user);
        }
    }

    @Override
    public void changePwd(int id, String pwd) {
        User user = new User();
        if (id > 0 && pwd != null && !pwd.equals("")) {
            user.setId(id);
            user.setPwd(pwd);
            userDao.updateUser(user);
        }
    }
}
