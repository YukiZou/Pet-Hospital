package com.ecnu.vo;

import com.ecnu.entity.Disease;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class DiseaseQueryVO  implements Serializable {
    private Integer id;
    private String name;
    private String category;

    public DiseaseQueryVO() {

    }

    public DiseaseQueryVO(Disease disease) {
        this.id = disease.getId();
        this.name = disease.getName();
        this.category = disease.getCategory();
    }
}

