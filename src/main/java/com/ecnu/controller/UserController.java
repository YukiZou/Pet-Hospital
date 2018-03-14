package com.ecnu.controller;

import com.ecnu.dto.UserDTO;
import com.ecnu.entity.User;
import com.ecnu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/user")
public class UserController {
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void addUser(@RequestBody UserDTO userDTO) {
        LOG.info("add user : {}", userDTO.toString());
        try {
            //UserDTO userDTO = new ObjectMapper().readValue(usr, UserDTO.class);
            //将UserDTO对象转化成User对象
            User user = new User();
            user.setUserName(userDTO.getUserName());
            user.setPwd(userDTO.getPwd());
            user.setAuth(userDTO.getAuth());
            user.setPictureUrl(userDTO.getPictureUrl());

            //调用userService层方法新增用户
            userService.addUser(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test
    @RequestMapping(value = "/listAll")
    public String listUsers(Map<String, Object> map) {
        List<User> users = userService.getUsers();
        map.put("users", users);
        return "userTest";
    }
}
