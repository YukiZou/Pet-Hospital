package com.ecnu.vo;

import com.ecnu.common.response.BaseResponse;
import com.ecnu.entity.Drug;
import lombok.Data;

import java.io.Serializable;

/**
 * 药品增加方法返回给前端的数据,封装了status
 */
@Data
public class DrugAddVO extends BaseResponse implements Serializable {
    private int id;
    private String name;
    private String info;

    public DrugAddVO() {

    }

    public DrugAddVO(String status) {
        super.setStatus(status);
    }

    public DrugAddVO(Drug drug) {
        this.id = drug.getId();
        this.name = drug.getName();
        this.info = drug.getInfo();
    }

}
