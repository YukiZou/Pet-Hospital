package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 病例的文字表述
 * @author asus
 */
@Data
public class Text implements Serializable{
    private int id;
    private int caseId;

    /**
     * 与CaseStageEnum枚举类对应，表示处于病例的哪个阶段的文字表示。
     */
    private int stage;
    private String info;
}
