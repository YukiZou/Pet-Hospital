package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 病例/流程的视频表示
 * 限制：case_id和procedure_id不能共存
 * 如果procedure_id字段有值的话，表示是和procedure关联的视频，case_id/stage字段无意义
 * 如果case_id字段有值的话，表示是和case关联的视频，procedure_id无意义，且stage表示是处于case的哪个阶段的视频表示
 * @author asus
 */
@Data
public class Video implements Serializable {
    private int id;
    private int caseId;

    /**
     * 流程管理的id，case_id与procedure_id不同时不为空
     */
    private int procedureId;

    /**
     * 与CaseStageEnum枚举类对应，表示处于病例的哪个阶段的图片表示。
     */
    private int stage;
    private String url;
}
