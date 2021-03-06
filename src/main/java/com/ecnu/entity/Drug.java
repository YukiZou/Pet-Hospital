package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 药物
 */
@Data
public class Drug implements Serializable{
    private int id;//药物ID
    private String name;//药品名称
    private String picture;
    private String info;
}
