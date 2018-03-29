package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 考题
 */
@Data
public class Question implements Serializable{
    private int id;
    private String category;//种类，如寄生虫病、内科病、传染病、外产科病、免疫病这种。
    private String stem;//题干部分
    private String A;
    private String B;
    private String C;
    private String D;
    private String answer;//A or B or C or D
}
