package com.ecnu.vo;

import com.ecnu.entity.Drug;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询药品信息列表时将返回的Drug对象转换成DrugVO
 * 不封装status字段
 */
@Data
public class DrugVO implements Serializable{
    private int id;
    private String name;
    private String info;

    public DrugVO() {

    }

    public DrugVO(Drug drug) {
        this.id = drug.getId();
        this.name = drug.getName();
        this.info = drug.getInfo();
    }
}
