package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 科室
 * @author asus
 */
@Data
public class Department implements Serializable{
    private int id;

    /**
     * 科室名字
     */
    private String name;

    /**
     * 负责人角色，与RoleEnum对应
     */
    private int role;
    private String info;

    /**
     * 科室图片url
     */
    private String picture;
}
