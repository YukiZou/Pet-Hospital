package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 将前端页面的删除条件id封装成DTO对象进行解析查询
 * @author asus
 */
@Data
public class UserDeleteDTO implements Serializable{
    private int id;
}
