package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 科室和药品关联图
 */

@Data
public class DepDrug implements Serializable{
    private int drugId;
    private int departmentId;
}
