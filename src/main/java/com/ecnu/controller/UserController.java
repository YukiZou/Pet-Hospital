package com.ecnu.controller;

import com.ecnu.common.response.BaseResponse;
import com.ecnu.dto.*;
import com.ecnu.vo.UserListVO;
import com.ecnu.entity.User;
import com.ecnu.service.UserService;
import com.ecnu.vo.UserQueryVO;
import com.ecnu.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/user")
//@SessionAttributes("loginUser")
public class UserController {
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户登录，登录成功，返回用户json字符串；登录不成功，返回null（这边需要改进）
     * 将成功登录的用户User对象放入session中（还未测试）
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/login", method= RequestMethod.POST)
    @ResponseBody
    public UserVO userLogin(@RequestBody UserDTO userDTO/*, Model model*/) {
        LOG.info("user {} login", userDTO.toString());
        try {
            if (!userDTO.getUserName().equals("") && !userDTO.getPwd().equals("")) {
                User user = toUser(userDTO);
                User loginUser = userService.checkLogin(user);
                UserVO userVO = new UserVO(loginUser);
                if (loginUser != null) {//success
                    LOG.info("user {} login success", user.getUserName());
                    userVO.setStatus("success");
                    //model.addAttribute("loginUser", loginUser);
                } else {
                    userVO.setStatus("fail");
                    LOG.error("用户登录失败");
                }
                LOG.info("userVO : {}", userVO.toString());
                return userVO;
            }
            LOG.error("用户名和密码不能为空");
            return new UserVO("fail");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("user login error!");
            return new UserVO("fail");
        }
    }

    /**
     * 新增用户
     * 需要改进：response自定义；登录用户权限鉴定；如何判断新增用户成功与否，返回值的问题。
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public UserVO addUser(@RequestBody UserDTO userDTO/*, @ModelAttribute("loginUser") User loginUser, Model model*/) {
        //LOG.info("add user : {} for admin {} user", userDTO.toString(), loginUser.getUserName());
        LOG.info("add user : {}", userDTO.toString());
        try {
            //UserDTO userDTO = new ObjectMapper().readValue(usr, UserDTO.class);
            //将UserDTO对象转化成User对象
            User user = toUser(userDTO);
            //给新增的用户一个默认的头像url
            user.setPictureUrl("D:\\petHospitalImages\\user.jpg");

            //调用userService层方法新增用户
            Boolean res = userService.addUser(user);

            if (res) {//新增用户成功
                UserVO userVO = new UserVO(user);
                LOG.info("add user : {} success", user.toString());
                userVO.setStatus("success");
                return userVO;
            } else {
                LOG.error("add user : {} failed", user.toString());
                return new UserVO("fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("add user failed");
            return new UserVO("fail");
        }
    }

    /**
     * 删除用户。
     * 需要改进：response自定义；登录用户权限鉴定；如何判断删除用户成功与否，返回值的问题。
     * @param userDeleteDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteUser(@RequestBody UserDeleteDTO userDeleteDTO) {
        LOG.info("delete user for id {}", userDeleteDTO.getId());
        try{
            Boolean res = userService.deleteUser(userDeleteDTO.getId());
            if (res) {
                LOG.info("delete user for user id {} success!", userDeleteDTO.getId());
                return new BaseResponse("success");
            } else {
                LOG.error("delete user for user id {} failed!", userDeleteDTO.getId());
                return new BaseResponse("fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("delete user failed");
            return new BaseResponse("fail");
        }

    }

    /**
     * 修改前台用户权限。
     * 需要改进：response自定义；登录用户权限鉴定；如何判断修改前台用户权限成功与否，返回值的问题。
     * @param userAuthDTO
     * @return
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse changeAuth(@RequestBody UserAuthDTO userAuthDTO) {
        try{
            int userId = userAuthDTO.getId();
            int auth = userAuthDTO.getAuth();
            LOG.info("change user {} with auth {}", userId, auth);

            Boolean res = userService.changeAuth(userId, auth);
            if (res) {
                LOG.info("change user auth for user id {} success!", userId);
                return new BaseResponse("success");
            } else {
                LOG.error("change user auth for user id {} failed!", userId);
                return new BaseResponse("fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("change user auth failed");
            return new BaseResponse("fail");
        }

    }


    /**
     * 修改密码 & 管理员重置用户密码。
     * 需要改进：response自定义；登录用户权限鉴定；如何判断修改密码成功与否，返回值的问题。
     * @param userPwdDTO
     * @return
     */
    @RequestMapping(value = "/pwd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse changePwd(@RequestBody UserPwdDTO userPwdDTO) {
        try {
            int userId = userPwdDTO.getId();
            String pwd = userPwdDTO.getPwd();
            LOG.info("change user {} password", userId);

            Boolean res = userService.changePwd(userId, pwd);
            if (res) {
                LOG.info("change user {} password success!", userId);
                return new BaseResponse("success");
            } else {
                LOG.error("change user {} password failed!", userId);
                return new BaseResponse("fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("change user password failed");
            return new BaseResponse("fail");
        }
    }

    /**
     * 根据当前登录用户的权限查询所有用户
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public UserListVO allUsers() {
        LOG.info("list all users");
        try {
            List<User> queryUsers = userService.queryUsers(new User());

            List<UserQueryVO> userQueryVOList = new LinkedList<>();
            for (User user: queryUsers) {
                UserQueryVO userQueryVO = new UserQueryVO(user);
                userQueryVOList.add(userQueryVO);
            }
            LOG.info("list all users success!");
            return new UserListVO("success", userQueryVOList);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("list all users failed");
            return new UserListVO("fail");
        }
    }

    /**
     * 根据条件查询符合条件的用户，模糊查询
     * 希望前端可以将查询的条件userName和auth封装成json对象用post方法传过来
     * @param userQueryDTO 封装查询条件
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public UserListVO queryUsers(@RequestBody UserQueryDTO userQueryDTO) {
        LOG.info("query users for filter {}", userQueryDTO.toString());
        try {
            User user = new User();
            String userName = userQueryDTO.getUserName();
            int auth = userQueryDTO.getAuth();
            if (userName != null && !userName.equals("")) {
                user.setUserName(userName);
            }
            if (auth > 0 && auth < 4) {
                user.setAuth(auth);
            }
            List<User> queryUsers = userService.queryUsers(user);
            List<UserQueryVO> userQueryVOList = new LinkedList<>();
            for (User queryUser: queryUsers) {
                UserQueryVO userQueryVO = new UserQueryVO(queryUser);
                userQueryVOList.add(userQueryVO);
            }
            LOG.info("query users for filter {} success!", userQueryDTO.toString());
            return new UserListVO("success", userQueryVOList);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("query users for filter {} failed", userQueryDTO.toString());
            return new UserListVO("fail");
        }
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
