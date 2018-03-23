package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 将前端页面的查询条件userName和auth封装成DTO对象进行解析查询
 */
@Data
public class UserQueryDTO implements Serializable{
    private String userName;
    private int auth;
}
