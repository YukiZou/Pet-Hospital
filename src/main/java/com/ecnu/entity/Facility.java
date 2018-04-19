package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备
 * @author asus
 */
@Data
public class Facility implements Serializable{
    /**
     * 设备ID
     */
    private int id;

    /**
     * 设备名称
     */
    private String name;
    private String picture;
    private String info;
}
