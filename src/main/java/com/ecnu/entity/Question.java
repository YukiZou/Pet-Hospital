package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 考题
 * @author asus
 */
@Data
public class Question implements Serializable{
    private int id;

    /**
     * 种类，如寄生虫病、内科病、传染病、外产科病、免疫病这种。
     */
    private String category;

    /**
     * 题干部分
     */
    private String stem;
    private String optA;
    private String optB;
    private String optC;
    private String optD;

    /**
     * A or B or C or D
     */
    private String answer;
}
