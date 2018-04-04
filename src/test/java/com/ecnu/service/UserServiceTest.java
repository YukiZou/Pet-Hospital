package com.ecnu.service;

import com.ecnu.entity.User;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest extends BaseServiceTest{
    @Resource
    private UserService userService;

    /**
     * user表中至少有一条为超级管理员（auth=3）的用户记录
     * @throws Exception
     */
    @Test
    public void getUsers() throws Exception {
        List<User> userList = userService.getUsers();
        List<Integer> userAuths = new ArrayList<>();
        for (User user : userList) {
            userAuths.add(user.getAuth());
        }
        assertTrue(userAuths.contains(3));
    }

    @Test
    public void queryUsers() throws Exception {
        //多条件存在的情况
        User user = new User();
        user.setUserName("zou");
        user.setAuth(3);
        List<User> userList = userService.queryUsers(user);
        Boolean[] expects = {true, true};
        for (User u : userList) {
            Boolean[] actuals = {u.getUserName().contains("zou"), u.getAuth() == 3};
            assertArrayEquals(expects, actuals);
        }

        //单条件不存在的情况
        user = new User();
        user.setUserName("11111111");
        userList = userService.queryUsers(user);
        assertTrue(userList.size() == 0);
    }

    @Test
    public void queryUserById() throws Exception {
        //该id存在的情况
        User queryUser = userService.queryUserById(31);
        assertTrue(queryUser.getId() == 31);

        //该id不存在的情况
        queryUser = userService.queryUserById(1);
        assertNull(queryUser);
    }

    /**
     * 是通过完整的name字段查找的，所以不同于之前对于name字段模糊匹配，所以返回值只有一个User对象，而不是一个List
     * @throws Exception
     */
    @Test
    public void queryUserByName() throws Exception {
        //正确查找到
        String name = "123456";
        User user = userService.queryUserByName(name);
        assertTrue(user.getUserName().equals(name));

        //找不到
        name = "zou";
        user = userService.queryUserByName(name);
        assertNull(user);
    }

    @Test
    public void addUser() throws Exception {
        //无重复名字
        User user = new User();
        String name = "testAdd" + System.currentTimeMillis();
        user.setUserName(name);
        user.setPwd("testPwd");
        user.setAuth(3);
        assertTrue(userService.addUser(user));
        int addId = user.getId();
        //重复加一次应该要报错
        assertFalse(userService.addUser(user));
        assertTrue(userService.deleteUser(addId));
    }

    /**
     * 保证测试方法的执行不影响数据库中的记录
     * @throws Exception
     */
    @Test
    public void deleteUser() throws Exception {
        String name = "testDelete" + System.currentTimeMillis();//保证测试名字唯一
        User addUser = new User();
        addUser.setUserName(name);
        addUser.setPwd("testPwd");
        addUser.setAuth(3);
        assertTrue(userService.addUser(addUser));
        int userId = addUser.getId();
        assertTrue(userService.deleteUser(userId));
        assertFalse(userService.deleteUser(userId));

    }

    @Test
    public void changeAuth() throws Exception {
        String name = "testChangeAuth" + System.currentTimeMillis();//保证测试名字唯一
        User addUser = new User();
        addUser.setUserName(name);
        addUser.setPwd("testPwd");
        addUser.setAuth(3);
        assertTrue(userService.addUser(addUser));
        int userId = addUser.getId();
        int auth = 2;
        assertTrue(userService.changeAuth(userId, auth));
        assertTrue(userService.deleteUser(userId));
        assertFalse(userService.changeAuth(userId, auth));
    }

    @Test
    public void changePwd() throws Exception {
        String name = "testChangePwd" + System.currentTimeMillis();//保证测试名字唯一
        User addUser = new User();
        addUser.setUserName(name);
        addUser.setPwd("testPwd");
        addUser.setAuth(3);
        assertTrue(userService.addUser(addUser));
        int id = addUser.getId();
        String pwd = "testChangePwd";
        assertTrue(userService.changePwd(id, pwd));
        assertTrue(userService.deleteUser(id));
        assertFalse(userService.changePwd(id, pwd));

    }

    @Test
    public void updateUser() throws Exception {
        String name = "testUpdate" + System.currentTimeMillis();//保证测试名字唯一
        User addUser = new User();
        addUser.setUserName(name);
        addUser.setPwd("testPwd");
        addUser.setAuth(3);
        assertTrue(userService.addUser(addUser));
        int id = addUser.getId();
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setPictureUrl("testUrl");
        assertTrue(userService.updateUser(updateUser));
        assertTrue(userService.deleteUser(id));
        assertFalse(userService.updateUser(updateUser));
    }

}