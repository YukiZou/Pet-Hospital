package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 科室药物的关联表
 */
@Data
public class DepFacility implements Serializable{
    private int departmentId;
    private int facilityId;

    public DepFacility(){

    }

    public DepFacility(int facilityId, int departmentId) {
        this.facilityId = facilityId;
        this.departmentId = departmentId;
    }
}
