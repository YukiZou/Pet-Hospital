package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 科室药物的关联表
 */
@Data
public class DepFacility implements Serializable{
    private int department_id;
    private int facility_id;
}
