package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 将前端页面的修改权限条件id, auth封装成DTO对象进行解析查询
 */
@Data
public class UserAuthDTO implements Serializable{
    private int id;
    private int auth;
}
