package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 病例的视频表示
 */
@Data
public class Video implements Serializable {
    private int id;
    private int case_id;
    private int stage;//与CaseStageEnum枚举类对应，表示处于病例的哪个阶段的图片表示。
    private String url;
}
