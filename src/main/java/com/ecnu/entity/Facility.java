package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备
 */
@Data
public class Facility implements Serializable{
    private int id;//设备ID
    private String name;//设备名称
    private String picture;
    private String info;
}
