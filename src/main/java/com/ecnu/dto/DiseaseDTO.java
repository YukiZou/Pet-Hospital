package com.ecnu.dto;

import com.ecnu.entity.Disease;
import lombok.Data;

import java.io.Serializable;

/**
 * 增加病种时前端传回的数据
 * @author asus
 */
@Data
public class DiseaseDTO implements Serializable {
    private String name;
    private String category;

    public DiseaseDTO(){

    }

    public DiseaseDTO(Disease disease){
        this.name=disease.getName();
        this.category=disease.getCategory();
    }
}
