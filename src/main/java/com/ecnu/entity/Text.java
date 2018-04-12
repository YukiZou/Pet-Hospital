package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 病例的文字表述
 */
@Data
public class Text implements Serializable{
    private int id;
    private int caseId;
    private int stage;//与CaseStageEnum枚举类对应，表示处于病例的哪个阶段的文字表示。
    private String info;
}
