package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 病例中每段的图片信息
 */
@Data
public class Picture implements Serializable{
    private int id;
    private int case_id;
    private int stage;//与CaseStageEnum枚举类对应，表示处于病例的哪个阶段的图片表示。
    private String url;
}
