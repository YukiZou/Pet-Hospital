package com.ecnu.service;

import com.ecnu.entity.User;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface UserService {

    //test
    List<User> getUsers();

    /**
     * 根据参数user的数据来查找符合条件的user记录
     * @param user
     * @return
     */
    List<User> queryUsers(User user);

    User queryUserById(int id);

    /**
     * 登录成功与否判定
     * @param usr
     * @return
     */
    User checkLogin(User usr);

    /**
     * 新增用户
     * @param usr
     */
    Boolean addUser(User usr);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Boolean deleteUser(int id);

    /**
     * 管理员修改指定用户权限
     * @param id
     * @param auth 新权限
     */
    Boolean changeAuth(int id, int auth);

    /**
     * 管理员重置密码/用户修改密码
     * @param id
     * @param pwd 新密码
     */
    Boolean changePwd(int id, String pwd);
}
