package com.ecnu.vo;

import com.ecnu.common.response.BaseResponse;
import com.ecnu.entity.Disease;
import lombok.Data;

import java.io.Serializable;

@Data
public class DiseaseAddVO extends BaseResponse implements Serializable {
    private int id;
    private String name;
    private String category;

    public DiseaseAddVO() {

    }

    public DiseaseAddVO(String status) {
        super.setStatus(status);
    }

    public DiseaseAddVO(Disease disease) {
        this.id = disease.getId();
        this.name=disease.getName();
        this.category=disease.getCategory();
    }
}
