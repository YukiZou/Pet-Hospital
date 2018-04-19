package com.ecnu.vo;

import com.ecnu.entity.Drug;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询药品信息列表时将返回的Drug对象转换成DrugVO
 * 不封装status字段
 * @author asus
 */
@Data
public class DrugVO implements Serializable{
    private Integer id;
    private String name;
    private String info;
    private String picture;

    public DrugVO() {

    }

    public DrugVO(Drug drug) {
        this.id = drug.getId();
        this.name = drug.getName();
        this.info = drug.getInfo();
        this.picture = drug.getPicture();
    }
}
