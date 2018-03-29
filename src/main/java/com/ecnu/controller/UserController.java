package com.ecnu.controller;

import com.ecnu.common.MD5Util;
import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.common.enums.UserAuthEnum;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/user")
public class UserController {
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户登录，登录成功，返回用户json字符串；登录不成功，返回null（这边需要改进）
     * 将成功登录的用户User对象放入session中
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/login", method= RequestMethod.POST)
    @ResponseBody
    public UserVO userLogin(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        LOG.info("user {} login", userDTO.toString());
        try {
            if (!userDTO.getUserName().equals("") && !userDTO.getPwd().equals("")) {
                User user = toUser(userDTO);
                User loginUser = userService.checkLogin(user);
                if (loginUser != null) {//success
                    UserVO userVO = new UserVO(loginUser);
                    LOG.info("user {} login success", user.getUserName());
                    userVO.setStatus(ResponseStatusEnum.SUCCESS.getDesc());
                    HttpSession session = request.getSession(true);//获取session, true表示如果没有，则新建一个session
                    session.setAttribute("loginUser", loginUser);//将loginUser存入session中，让其他方法可以访问到。
                    return userVO;
                    //return new ObjectMapper().writeValueAsString(userVO);//对象转JSON字符串
                } else {
                    LOG.error("用户登录失败");
                    return new UserVO(ResponseStatusEnum.SQL_FAIL.getDesc());
                }
            }
            LOG.error("用户名和密码不能为空");
            return new UserVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("user login error!");
            return new UserVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 用户注销(还未测试正确性)
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method= RequestMethod.POST)
    @ResponseBody
    public BaseResponse userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
    }

    /**
     * 新增用户
     * 普通admin只能增加auth=1的user，superadmin可以增加任何auth的user
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public UserVO addUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");//得到session里存储的当前登录用户数据。
        LOG.info("add user : {} for loginUser {}", userDTO.toString(), loginUser.getUserName());
        try {
            //将UserDTO对象转化成User对象
            User user = toUser(userDTO);

            //获得项目的路径
            //ServletContext sc = request.getSession().getServletContext();
            //获得默认头像存储位置
            //String pictureUrl

            //给新增的用户一个默认的头像url
            //TODO:http://www.ecnupet.cn:8080/pet/img/logo.jpg
            user.setPictureUrl("ecnupet.cn:8080/pet/img/logo.jpg");

            Boolean res = false;
            //int loginUserAuth = loginUser.getAuth();
            //int addUserAuth = user.getAuth();
            UserAuthEnum loginUserAuth = UserAuthEnum.getUserAuthEnum(loginUser.getAuth());
            UserAuthEnum addUserAuth = UserAuthEnum.getUserAuthEnum(user.getAuth());

            //判断用户权限
            switch (loginUserAuth) {
                case ORDINARY_USER:
                    LOG.info("loginUser has no permissions to add current user");
                    break;
                case ADMIN://登录用户是普通管理员
                    switch (addUserAuth) {
                        case ORDINARY_USER:
                            res = userService.addUser(user);//可以增加普通前台用户
                            break;
                        default:
                            LOG.info("loginUser has no permissions to add current user");
                            break;
                    }
                    break;
                case SUPER_ADMIN://超级管理员
                    res = userService.addUser(user);//调用userService层方法新增用户
                    break;
            }

            if (res) {//新增用户成功
                UserVO userVO = new UserVO(user);
                LOG.info("add user : {} success", user.toString());
                userVO.setStatus(ResponseStatusEnum.SUCCESS.getDesc());
                return userVO;
            } else {
                LOG.error("add user : {} failed", user.toString());
                return new UserVO(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("add user failed");
            return new UserVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 删除用户。
     * 普通管理员只可以删除普通用户，超级管理员可以删除所有用户。
     * @param userDeleteDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteUser(@RequestBody UserDeleteDTO userDeleteDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");//得到session里存储的当前登录用户数据。
        LOG.info("delete user with id {} for loginUser {}", userDeleteDTO.getId(), loginUser.getUserName());
        try{
            Boolean res = false;
            int deleteUserId = userDeleteDTO.getId();
            User deleteUser = userService.queryUserById(deleteUserId);
            //如果当前登录用户与要删除的用户一致，则删除失败
            if (loginUser.getId() == deleteUser.getId()) {
                LOG.error("delete user failed : cannot delete current login user");
                return new BaseResponse(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }
            //int loginUserAuth = loginUser.getAuth();
            //int deleteUserAuth = deleteUser.getAuth();
            UserAuthEnum loginUserAuth = UserAuthEnum.getUserAuthEnum(loginUser.getAuth());
            UserAuthEnum deleteUserAuth = UserAuthEnum.getUserAuthEnum(deleteUser.getAuth());
            //判断用户权限
            switch (loginUserAuth) {
                case ORDINARY_USER:
                    LOG.info("loginUser has no permissions to delete current user");
                    break;
                case ADMIN://登录用户是普通管理员
                    switch (deleteUserAuth) {
                        case ORDINARY_USER://可以删除普通前台用户
                            res = userService.deleteUser(deleteUserId);
                            break;
                        default:
                            LOG.info("loginUser has no permissions to delete current user");
                            break;
                    }
                    break;
                case SUPER_ADMIN://超级管理员
                    res = userService.deleteUser(deleteUserId);
                    break;
            }

            if (res) {
                LOG.info("delete user for user id {} success!", userDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("delete user for user id {} failed!", userDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("delete user failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }

    /**
     * 修改前台用户权限。
     * 只有超级管理员能修改用户权限
     * 限制：当前登录用户不能修改自己的权限
     * @param userAuthDTO
     * @return
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse changeAuth(@RequestBody UserAuthDTO userAuthDTO, HttpServletRequest request) {
        try{
            HttpSession session = request.getSession();
            User loginUser = (User) session.getAttribute("loginUser");//得到session里存储的当前登录用户数据。
            int userId = userAuthDTO.getId();
            int auth = userAuthDTO.getAuth();
            LOG.info("change user {} with auth {} for loginUser {}", userId, auth, loginUser.getUserName());

            //不能修改当前登录用户的权限
            if (loginUser.getId() == userId) {
                LOG.error("change auth error: cannot change current login user's auth");
                return new BaseResponse(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }

            Boolean res = false;
            //int loginUserAuth = loginUser.getAuth();
            UserAuthEnum loginUserAuth = UserAuthEnum.getUserAuthEnum(loginUser.getAuth());
            switch (loginUserAuth) {
                case SUPER_ADMIN:
                    res = userService.changeAuth(userId, auth);
                    break;
                default:
                    LOG.info("loginUser has no permissions to change current user's auth");
                    break;
            }

            if (res) {
                LOG.info("change user auth for user id {} success!", userId);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("change user auth for user id {} failed!", userId);
                return new BaseResponse(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("change user auth failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }


    /**
     * 修改密码 & 管理员重置用户密码。
     * 普通admin只能修改自己/普通用户的密码，无权限修改其他普通admin/superadmin的密码
     * superadmin可以修改其他superadmin的密码,有所有权限
     * @param userPwdDTO
     * @return
     */
    @RequestMapping(value = "/pwd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse changePwd(@RequestBody UserPwdDTO userPwdDTO, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            User loginUser = (User) session.getAttribute("loginUser");//得到session里存储的当前登录用户数据。
            int userId = userPwdDTO.getId();
            String pwd = userPwdDTO.getPwd();//明文密码
            pwd = MD5Util.toMd5(pwd); //对密码加密后存入数据库中。
            LOG.info("change user {} password for loginUser {}", userId, loginUser.getUserName());

            Boolean res = false;
            User changePwdUser = userService.queryUserById(userId);
            //int loginUserAuth = loginUser.getAuth();
            //int changePwdUserAuth = changePwdUser.getAuth();
            UserAuthEnum loginUserAuth = UserAuthEnum.getUserAuthEnum(loginUser.getAuth());
            UserAuthEnum changePwdUserAuth = UserAuthEnum.getUserAuthEnum(changePwdUser.getAuth());
            //判断用户权限
            switch (loginUserAuth) {
                case ORDINARY_USER:
                    LOG.info("loginUser has no permissions to change current user's pwd");
                    break;
                case ADMIN://登录用户是普通管理员
                    switch (changePwdUserAuth) {
                        case ORDINARY_USER://可以修改普通前台用户密码
                            res = userService.changePwd(userId, pwd);
                            break;
                        case ADMIN://如果是普通管理员，可以先判断是不是更改本人的密码
                            if (userId == loginUser.getId()) {//当前登录用户修改自己的密码
                                res = userService.changePwd(userId, pwd);
                            }
                            break;
                        default:
                            LOG.info("loginUser has no permissions to change current user's pwd");
                            break;
                    }
                    break;
                case SUPER_ADMIN:
                    res = userService.changePwd(userId, pwd);
                    break;
            }

            if (res) {
                LOG.info("change user {} password success!", userId);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("change user {} password failed!", userId);
                return new BaseResponse(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("change user password failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 根据当前登录用户的权限查询所有用户
     * 两种admin均能查询到所有的用户列表。
     * 普通用户无权查看用户列表。
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public UserListVO allUsers(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            User loginUser = (User) session.getAttribute("loginUser");//得到session里存储的当前登录用户数据。
            LOG.info("list all users for loginUser {}", loginUser.getUserName());
            //int loginUserAuth = loginUser.getAuth();
            UserAuthEnum loginUserAuth = UserAuthEnum.getUserAuthEnum(loginUser.getAuth());
            if (UserAuthEnum.isOrdinaryUser(loginUserAuth)) {
                LOG.info("loginUser has no permissions to query user list");
                return new UserListVO(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }
            List<User> queryUsers = userService.queryUsers(new User());

            List<UserQueryVO> userQueryVOList = new LinkedList<>();
            for (User user: queryUsers) {
                UserQueryVO userQueryVO = new UserQueryVO(user);
                userQueryVOList.add(userQueryVO);
            }
            LOG.info("list all users success!");
            return new UserListVO(ResponseStatusEnum.SUCCESS.getDesc(), userQueryVOList);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("list all users failed");
            return new UserListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 根据条件查询符合条件的用户，模糊查询
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
            return new UserListVO(ResponseStatusEnum.SUCCESS.getDesc(), userQueryVOList);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("query users for filter {} failed", userQueryDTO.toString());
            return new UserListVO(ResponseStatusEnum.FAIL.getDesc());
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
        if (userDTO.getPwd() != null && !userDTO.getPwd().equals("")) {
            user.setPwd(MD5Util.toMd5(userDTO.getPwd()));//将DTO中明文的字符串加密后存在User对象中。
        }
        //user.setPwd(userDTO.getPwd());
        user.setAuth(userDTO.getAuth());
        user.setPictureUrl(userDTO.getPictureUrl());
        return user;
    }
}
