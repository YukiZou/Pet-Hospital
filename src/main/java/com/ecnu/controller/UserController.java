package com.ecnu.controller;

import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.common.enums.UserAuthEnum;
import com.ecnu.common.BaseResponse;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.ecnu.common.CheckInputStringUtil.containIllegalCharacter;

/**
 * @author asus
 */
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
        try {
            LOG.info("user {} login", userDTO.toString());
            String loginUserName = userDTO.getUserName();
            String loginUserPwd = userDTO.getPwd();
            if (loginUserName == null || "".equals(loginUserName) || loginUserPwd == null || "".equals(loginUserPwd)) {
                LOG.error("用户名或密码不能为空");
                return new UserVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

            User loginUser = userService.queryUserByName(loginUserName);
            if (loginUser == null) {
                LOG.error("user {} do not exist!", loginUserName);
                //登录用户的用户名不存在
                return new UserVO(ResponseStatusEnum.NO_USER_FAIL.getDesc());
            }

            //登录成功
            if (loginUser.getPwd().equals(loginUserPwd)) {
                UserVO userVO = new UserVO(loginUser);
                LOG.info("user {} login success", loginUserName);
                userVO.setStatus(ResponseStatusEnum.SUCCESS.getDesc());
                //获取session, true表示如果没有，则新建一个session
                HttpSession session = request.getSession(true);
                //将loginUser存入session中，让其他方法可以访问到。
                session.setAttribute("loginUser", loginUser);
                return userVO;
            } else {
                LOG.error("user {} login failed for error password!", loginUserName);
                return new UserVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

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
        LOG.info("start logout!");
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
        try {
            String addUserName = userDTO.getUserName();
            String addUserPwd = userDTO.getPwd();
            int auth = userDTO.getAuth();
            if (addUserName == null || "".equals(addUserName) || addUserPwd == null || "".equals(addUserPwd)) {
                LOG.error("add user for input error!");
                return new UserVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

            if (containIllegalCharacter(addUserName)) {
                LOG.error("name is invalid!");
                return new UserVO(ResponseStatusEnum.INVALID_INPUT_FAIL.getDesc());
            }

            HttpSession session = request.getSession();
            //得到session里存储的当前登录用户数据。
            User loginUser = (User) session.getAttribute("loginUser");
            LOG.info("add user : {} for loginUser {}", userDTO.toString(), loginUser.getUserName());

            //将UserDTO对象转化成User对象
            User user = toUser(userDTO);


            //给新增的用户一个默认的头像url
            user.setPictureUrl("http://ecnupet.cn/img/sijia.jpg");

            UserAuthEnum loginUserAuth = UserAuthEnum.getUserAuthEnum(loginUser.getAuth());
            UserAuthEnum addUserAuth = UserAuthEnum.getUserAuthEnum(auth);

            Boolean hasAuth = true;

            //判断用户权限
            switch (loginUserAuth) {
                case ORDINARY_USER:
                    hasAuth = false;
                    break;
                case ADMIN:
                    //登录用户是普通管理员
                    switch (addUserAuth) {
                        case ORDINARY_USER:
                            //可以增加普通前台用户
                            break;
                        default:
                            hasAuth = false;
                            break;
                    }
                    break;
                case SUPER_ADMIN:
                    //超级管理员
                    break;
                default:
                    break;
            }

            //如果登录用户没有权限
            if (!hasAuth) {
                LOG.error("loginUser has no permissions to add current user");
                return new UserVO(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }

            //如果数据库表中已经存在该用户，返回对应错误类型
            User queryUser = userService.queryUserByName(addUserName);
            if (queryUser != null) {
                LOG.error("addUserName: {} has existed!", addUserName);
                return new UserVO(ResponseStatusEnum.DUPLICATE_USERNAME_FAIL.getDesc());
            }

            Boolean res = userService.addUser(user);

            if (res) {
                //新增用户成功
                UserVO userVO = new UserVO(user);
                LOG.info("add user : {} success", user.toString());
                userVO.setStatus(ResponseStatusEnum.SUCCESS.getDesc());
                return userVO;
            } else {
                LOG.error("add user : {} failed", user.toString());
                return new UserVO(ResponseStatusEnum.SQL_FAIL.getDesc());
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
        try{
            HttpSession session = request.getSession();
            //得到session里存储的当前登录用户数据。
            User loginUser = (User) session.getAttribute("loginUser");
            LOG.info("delete user with id {} for loginUser {}", userDeleteDTO.getId(), loginUser.getUserName());


            int deleteUserId = userDeleteDTO.getId();
            User deleteUser = userService.queryUserById(deleteUserId);
            //如果没找到，说明数据库中无该用户
            if (deleteUser == null) {
                LOG.error("user for id {} do not exist!",deleteUserId);
                return new BaseResponse(ResponseStatusEnum.NO_USER_FAIL.getDesc());
            }

            Boolean hasAuth = true;
            UserAuthEnum loginUserAuth = UserAuthEnum.getUserAuthEnum(loginUser.getAuth());
            UserAuthEnum deleteUserAuth = UserAuthEnum.getUserAuthEnum(deleteUser.getAuth());
            //判断用户权限
            switch (loginUserAuth) {
                case ORDINARY_USER:
                    hasAuth = false;
                    break;
                case ADMIN:
                    //登录用户是普通管理员
                    switch (deleteUserAuth) {
                        case ORDINARY_USER:
                            //可以删除普通前台用户
                            break;
                        default:
                            hasAuth = false;
                            break;
                    }
                    break;
                case SUPER_ADMIN:
                    //超级管理员
                    if (loginUser.getId() == deleteUser.getId()) {
                        //超级管理员无权删除自己
                        hasAuth = false;
                    }
                    break;
                default:
                    break;
            }

            //如果登录用户没有权限
            if (!hasAuth) {
                LOG.error("loginUser has no permissions to delete current user");
                return new UserVO(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }

            Boolean res = userService.deleteUser(deleteUserId);

            if (res) {
                LOG.info("delete user for user id {} success!", userDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.info("loginUser has no permissions to delete current user");
                return new BaseResponse(ResponseStatusEnum.SQL_FAIL.getDesc());
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
            //得到session里存储的当前登录用户数据。
            User loginUser = (User) session.getAttribute("loginUser");
            int userId = userAuthDTO.getId();
            int auth = userAuthDTO.getAuth();
            if (userId <= 0) {
                LOG.error("userId invalid!");
                return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }
            LOG.info("change user {} with auth {} for loginUser {}", userId, auth, loginUser.getUserName());

            User queryUser = userService.queryUserById(userId);
            if (queryUser == null) {
                LOG.error("user for id {} do not exist!", userId);
                return new BaseResponse(ResponseStatusEnum.NO_USER_FAIL.getDesc());
            }

            Boolean hasAuth = true;
            UserAuthEnum loginUserAuth = UserAuthEnum.getUserAuthEnum(loginUser.getAuth());
            switch (loginUserAuth) {
                case SUPER_ADMIN:
                    //超级管理员不能修改自己的权限
                    if (loginUser.getId() == userId) {
                        hasAuth = false;
                    }
                    break;
                default:
                    hasAuth = false;
                    break;
            }

            if (!hasAuth) {
                LOG.info("loginUser has no permissions to change current user's auth");
                return new BaseResponse(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }

            Boolean res = userService.changeAuth(userId, auth);

            if (res) {
                LOG.info("change user auth for user id {} success!", userId);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("change user auth for user id {} failed!", userId);
                return new BaseResponse(ResponseStatusEnum.SQL_FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("change user auth failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }


    /**
     * 管理员重置用户密码。
     * 普通admin只能重置普通用户的密码，无权限重置其他普通admin/superadmin的密码
     * superadmin可以修改其他superadmin的密码（包括自己的密码）,有所有权限
     * @param userPwdDTO
     * @return
     */
    @RequestMapping(value = "/pwd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse changePwd(@RequestBody UserPwdDTO userPwdDTO, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            //得到session里存储的当前登录用户数据。
            User loginUser = (User) session.getAttribute("loginUser");
            int userId = userPwdDTO.getId();
            String pwd = userPwdDTO.getPwd();
            if (userId <= 0 || pwd == null || "".equals(pwd)) {
                LOG.error("userId or pwd invalid!");
                return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }
            LOG.info("change user {} password for loginUser {}", userId, loginUser.getUserName());

            User queryUser = userService.queryUserById(userId);
            if (queryUser == null) {
                LOG.error("user for id {} do not exist!", userId);
                return new BaseResponse(ResponseStatusEnum.NO_USER_FAIL.getDesc());
            }

            Boolean hasAuth = true;
            UserAuthEnum loginUserAuth = UserAuthEnum.getUserAuthEnum(loginUser.getAuth());
            UserAuthEnum changePwdUserAuth = UserAuthEnum.getUserAuthEnum(queryUser.getAuth());
            //判断用户权限
            switch (loginUserAuth) {
                case ORDINARY_USER:
                    hasAuth = false;
                    break;
                case ADMIN:
                    //登录用户是普通管理员
                    switch (changePwdUserAuth) {
                        case ORDINARY_USER:
                            //可以修改普通前台用户密码
                            break;
                        case ADMIN:
                            //如果是普通管理员，可以先判断是不是更改本人的密码
                            if (userId != loginUser.getId()) {
                                //当前登录用户只能修改自己的密码，不可修改其他普通管理员用户密码
                                hasAuth = false;
                            }
                            break;
                        default:
                            hasAuth = false;
                            break;
                    }
                    break;
                case SUPER_ADMIN:
                    break;
                default:
                    break;
            }
            if (!hasAuth) {
                LOG.info("loginUser has no permissions to change current user's pwd");
                return new BaseResponse(ResponseStatusEnum.AUTH_FAIL.getDesc());
            }

            Boolean res = userService.changePwd(userId, pwd);
            if (res) {
                LOG.info("change user {} password success!", userId);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("change user {} password failed!", userId);
                return new BaseResponse(ResponseStatusEnum.SQL_FAIL.getDesc());
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
            //得到session里存储的当前登录用户数据。
            User loginUser = (User) session.getAttribute("loginUser");
            LOG.info("list all users for loginUser {}", loginUser.getUserName());
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
        try {
            LOG.info("query users for filter {}", userQueryDTO.toString());
            User user = new User();
            String userName = userQueryDTO.getUserName();
            int auth = userQueryDTO.getAuth();
            if (userName != null && !"".equals(userName)) {
                user.setUserName(userName);
            }
            if (auth > 0) {
                user.setAuth(auth);
            }
            List<User> queryUsers = userService.queryUsers(user);
            List<UserQueryVO> userQueryVOList = new ArrayList<>();
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


    /**
     * 当前登录的管理员用户修改自身的用户信息
     * 包括修改自身的昵称（唯一）、密码、头像
     * @param userSelfUpdateDTO
     * @param request
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public BaseResponse userSelfUpdate(@RequestBody UserSelfUpdateDTO userSelfUpdateDTO, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            //得到session里存储的当前登录用户数据。
            User loginUser = (User) session.getAttribute("loginUser");
            LOG.info("start update loginUser {} data for {}", loginUser.getUserName(), userSelfUpdateDTO.toString());
            int id = loginUser.getId();
            String name = userSelfUpdateDTO.getUserName();
            String pwd = userSelfUpdateDTO.getPwd();
            String picture = userSelfUpdateDTO.getPictureUrl();
            if (name == null && pwd == null && picture == null) {
                LOG.error("用户新昵称、用户新密码、用户新头像不能同时为空");
                return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }
            //新昵称不能包含特殊字符<>
            if (name != null && containIllegalCharacter(name)){
                LOG.error("name is invalid!");
                return new BaseResponse(ResponseStatusEnum.INVALID_INPUT_FAIL.getDesc());
            }
            //新昵称要唯一
            if (name != null && !"".equals(name)) {
                User queryUser = userService.queryUserByName(name);
                if (queryUser != null) {
                    LOG.error("user name has existed! update login user data error!");
                    return new BaseResponse(ResponseStatusEnum.DUPLICATE_USERNAME_FAIL.getDesc());
                }
            }

            User updateUser = new User(id, name, pwd, picture);
            Boolean res = userService.updateUser(updateUser);
            //修改当前登录用户信息成功，则更新session中的值。
            if (res) {
                LOG.info("update loginUser data success!");
                User newLoginUser = userService.queryUserById(id);
                session.setAttribute("loginUser", newLoginUser);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("update loginUser data failed!");
                return new BaseResponse(ResponseStatusEnum.SQL_FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("update loginUser for userSelfUpdateDTO {} failed", userSelfUpdateDTO.toString());
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * test
     */
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
