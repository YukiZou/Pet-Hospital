package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 科室和药品关联图
 * @author asus
 */

@Data
public class DepDrug implements Serializable{
    private int drugId;
    private int departmentId;

    public DepDrug(){

    }

    public DepDrug(int drugId, int departmentId) {
        this.drugId = drugId;
        this.departmentId = departmentId;
    }
}
