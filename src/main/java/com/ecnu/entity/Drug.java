package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 药物
 * @author asus
 */
@Data
public class Drug implements Serializable{
    /**
     * 药物ID
     */
    private int id;

    /**
     * 药品名称
     */
    private String name;
    private String picture;
    private String info;
}
