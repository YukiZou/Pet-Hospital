package com.ecnu.dao;

import com.ecnu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    //test
    List<User> getAllUser();

    /**
     * 根据userName找User记录
     * @param userName
     * @return
     */
    User findUserByName(String userName);

    /**
     * 根据ID找到User记录
     * @param id
     * @return
     */
    User findUserById (int id);

    /**
     * 根据参数usr查询出所有符合查询条件的user用户,可以实现模糊查找
     * @param usr
     * @return
     */
    List<User> queryUsers(User usr);

    /**
     * 新增一条用户信息
     * @param usr
     */
    void insertUser(User usr);

    /**
     * 根据参数usr的ID找到要更改的用户记录，然后update该条记录
     * 可修改权限auth,修改密码
     * @param usr
     */
    int updateUser(User usr);

    /**
     * 根据参数usr删除指定用户记录。
     * @param usr
     */
    int deleteUser(User usr);
}
