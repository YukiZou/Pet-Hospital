package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 病例/流程中图片信息
 * 限制：case_id和procedure_id不能共存
 * 如果procedure_id字段有值的话，表示是和procedure关联的图片，case_id/stage字段无意义
 * 如果case_id字段有值的话，表示是和case关联的图片，procedure_id无意义，且stage表示是处于case的哪个阶段的图片表示
 */
@Data
public class Picture implements Serializable{
    private int id;
    private int caseId; //关联的病例id
    private int procedureId;//流程管理的id，case_id与procedure_id不能同时不为空
    private int stage;//与CaseStageEnum枚举类对应，表示处于病例的哪个阶段的图片表示；如果只与procedure_id关联的话，此字段无意义
    private String url;
}
