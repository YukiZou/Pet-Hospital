package com.ecnu.service;

import com.ecnu.entity.User;

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
     * 根据用户名来查找user
     * @param name
     * @return
     */
    User queryUserByName(String name);

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

    /**
     * 管理员用户修改自身的信息
     * @param user
     * @return
     */
    Boolean updateUser(User user);
}
