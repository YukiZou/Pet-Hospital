package com.ecnu.service.impl;

import com.ecnu.dao.UserDao;
import com.ecnu.entity.User;
import com.ecnu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
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
    public Boolean addUser(User usr) {
        String userName = usr.getUserName();
        String pwd =usr.getPwd();
        int auth = usr.getAuth();
        if (userName == null || userName.equals("") || pwd == null || pwd.equals("") || auth <= 0 || auth > 3) {//不合法输入
            LOG.error("新增用户信息不符合规范，新增用户失败。");
            return false;
        } else {
            //判断新增用户的userName在数据库中是否已经存在
            User findUser = userDao.findUserByName(userName);
            if (findUser != null && findUser.getId() > 0) {
                LOG.error("该用户名已经存在，新增用户失败。");
                return false;
            }
            userDao.insertUser(usr);
            if (usr.getId() > 0) {
                LOG.info("新增用户成功。");
                return true;
            } else {
                LOG.info("新增用户失败。");
                return false;
            }
        }
    }

    @Override
    public Boolean deleteUser(int id) {
        User user = new User();
        if (id > 0) {
            user.setId(id);
            int res = userDao.deleteUser(user);
            if (res > 0) {
                LOG.info("删除用户 {} 成功", id);
                return true;
            } else {
                LOG.error("删除用户失败");
                return false;
            }
        } else {
            LOG.error("用户id不合法");
            return false;
        }
    }

    @Override
    public Boolean changeAuth(int id, int auth) {
        User user = new User();
        if (id > 0 && auth > 0 && auth < 4) {
            user.setId(id);
            List<User> queryUserList = userDao.queryUsers(user);
            if (queryUserList.size() != 1) {//此用户不存在
                LOG.error("该用户不存在，请确定用户ID");
                return false;
            }
            user.setAuth(auth);
            int res = userDao.updateUser(user);
            if (res > 0) {
                LOG.info("修改用户权限成功");
                return true;
            } else {
                LOG.error("修改用户权限失败");
                return false;
            }
        } else {
            LOG.error("用户id和auth不合法");
            return false;
        }
    }

    @Override
    public Boolean changePwd(int id, String pwd) {
        User user = new User();
        if (id > 0 && pwd != null && !pwd.equals("")) {
            user.setId(id);
            List<User> queryUserList = userDao.queryUsers(user);
            if (queryUserList.size() != 1) {//此用户不存在
                LOG.error("该用户不存在，请确定用户ID");
                return false;
            }
            user.setPwd(pwd);
            int res = userDao.updateUser(user);
            if (res > 0) {
                LOG.info("修改/重置用户密码成功");
                return true;
            } else {
                LOG.error("修改/重置用户密码失败");
                return false;
            }

        } else {
            LOG.error("用户id和password不合法");
            return false;
        }
    }
}
