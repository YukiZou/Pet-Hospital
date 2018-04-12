package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 科室
 */
@Data
public class Department implements Serializable{
    private int id;
    private String name;//科室名字
    private int role;//负责人角色，与RoleEnum对应
    private String info;
    private String picture;//科室图片url
}
