package com.ecnu.controller;

import com.ecnu.dto.ListUserDTO;
import com.ecnu.dto.QueryUserDTO;
import com.ecnu.dto.UserDTO;
import com.ecnu.entity.User;
import com.ecnu.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/user")
@SessionAttributes("loginUser")
public class UserController {
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户登录，登录成功，返回用户json字符串；登录不成功，返回null（这边需要改进）
     * 将成功登录的用户User对象放入session中（还未测试）
     * @param userDTO
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method= RequestMethod.POST)
    @ResponseBody
    public String userLogin(@RequestBody UserDTO userDTO, Model model) {
        LOG.info("user {} login", userDTO.toString());
        try {
            if (!userDTO.getUserName().equals("") && !userDTO.getPwd().equals("")) {
                User user = toUser(userDTO);
                User loginUser = userService.checkLogin(user);
                if (loginUser != null) {//success
                    LOG.info("user {} login success", user.getUserName());
                    model.addAttribute("loginUser", loginUser);
                } else {
                    LOG.info("user login error!");
                }
                return new ObjectMapper().writeValueAsString(loginUser);
                //return "userTest";//test
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增用户
     * 需要改进：response自定义；登录用户权限鉴定；如何判断新增用户成功与否，返回值的问题。
     * @param userDTO
     * @param loginUser
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestBody UserDTO userDTO, @ModelAttribute("loginUser") User loginUser, Model model) {
        LOG.info("add user : {} for admin {} user", userDTO.toString(), loginUser.getUserName());
        try {
            //UserDTO userDTO = new ObjectMapper().readValue(usr, UserDTO.class);
            //将UserDTO对象转化成User对象
            User user = toUser(userDTO);
            //给新增的用户一个默认的头像url
            user.setPictureUrl("D:\\petHospitalImages\\user.jpg");

            //调用userService层方法新增用户
            userService.addUser(user);
            LOG.info("add user : {} success", user.toString());
            UserDTO reUserDTO = new UserDTO(user);
            return new ObjectMapper().writeValueAsString(reUserDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除用户。
     * 需要改进：response自定义；登录用户权限鉴定；如何判断删除用户成功与否，返回值的问题。
     * @param userId
     * @return
     */
    //TODO:与接口定义不一致，如果只提供id,用get方法，如果提供json字符串，用post方法

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public String deleteUser(@RequestParam("id") int userId) {
        LOG.info("delete user for id {}", userId);
        userService.deleteUser(userId);
        LOG.info("delete user success!");
        return "success";
    }

    /**
     * 修改前台用户权限。
     * 需要改进：response自定义；登录用户权限鉴定；如何判断修改前台用户权限成功与否，返回值的问题。
     * @param userId int
     * @param auth int
     * @return
     */
    //TODO:与接口定义不一致，如果只提供id, auth,用get方法，如果提供json字符串，用post方法

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ResponseBody
    public String changeAuth(@RequestParam("id") int userId, @RequestParam("auth") int auth) {
        LOG.info("change user {} with auth {}", userId, auth);
        userService.changeAuth(userId, auth);
        LOG.info("change user auth success!");
        return "success";
    }

    /**
     * 修改密码 & 管理员重置用户密码。
     * 需要改进：response自定义；登录用户权限鉴定；如何判断修改密码成功与否，返回值的问题。
     * @param userId int
     * @param pwd String新密码
     * @return
     */
    //TODO:与接口定义不一致，如果只提供id, pwd,用get方法，如果提供json字符串，用post方法

    @RequestMapping(value = "/pwd", method = RequestMethod.GET)
    @ResponseBody
    public String changePwd(@RequestParam("id") int userId, @RequestParam("pwd") String pwd) {
        LOG.info("change user {} password", userId);
        userService.changePwd(userId, pwd);
        LOG.info("change user password success!");
        return "success";
    }

    /**
     * 根据当前登录用户的权限查询所有用户
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<ListUserDTO> allUsers() {
        LOG.info("list all users");
        List<User> queryUsers = userService.queryUsers(new User());
        List<ListUserDTO> resUsers = new LinkedList<>();
        for (User user: queryUsers) {
            ListUserDTO listUserDTO = new ListUserDTO(user);
            resUsers.add(listUserDTO);
        }
        LOG.info("list all users success!");
        return resUsers;
    }

    /**
     * 根据条件查询符合条件的用户，模糊查询
     * 希望前端可以将查询的条件userName和auth封装成json对象用post方法传过来
     * @param queryUserDTO 封装查询条件
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public List<ListUserDTO> queryUsers(@RequestBody QueryUserDTO queryUserDTO) {
        LOG.info("query users for filter {}", queryUserDTO.toString());
        User user = new User();
        String userName = queryUserDTO.getUserName();
        int auth = queryUserDTO.getAuth();
        if (userName != null && !userName.equals("")) {
            user.setUserName(userName);
        }
        if (auth > 0 && auth < 4) {
            user.setAuth(auth);
        }
        List<User> queryUsers = userService.queryUsers(user);
        List<ListUserDTO> resUsers = new LinkedList<>();
        for (User queryUser: queryUsers) {
            ListUserDTO listUserDTO = new ListUserDTO(queryUser);
            resUsers.add(listUserDTO);
        }
        LOG.info("query users for filter {} success!", queryUserDTO.toString());
        return resUsers;
    }

    //test
    @RequestMapping(value = "/listAll")
    public String listUsers(Map<String, Object> map) {
        List<User> users = userService.getUsers();
        map.put("users", users);
        return "userTest";
    }


    /**
     * 将UserDTO对象转化成User对象
     * @param userDTO
     * @return
     */
    private User toUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPwd(userDTO.getPwd());
        user.setAuth(userDTO.getAuth());
        user.setPictureUrl(userDTO.getPictureUrl());
        return user;
    }
}
