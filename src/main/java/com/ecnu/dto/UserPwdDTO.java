package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 将前端页面的修改密码条件id, pwd封装成DTO对象进行解析查询
 * @author asus
 */
@Data
public class UserPwdDTO implements Serializable{
    private int id;
    private String pwd;
}
