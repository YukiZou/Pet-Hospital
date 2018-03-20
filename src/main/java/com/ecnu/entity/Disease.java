package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 病种
 */
@Data
public class Disease implements Serializable{
    private int id;
    private String name;//病种名，如蛔虫病。
    private String category;//大类别，如寄生虫病
}
