package com.ecnu.vo;

import com.ecnu.entity.Case;
import com.ecnu.entity.Disease;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class CaseQueryVO implements Serializable {
    private int id;
    private String name;
    private String diseaseName;
    private String categoryName;

    public CaseQueryVO() {

    }

    public CaseQueryVO(Case c, Disease d) {
        this.id = c.getId();
        this.name = c.getName();
        this.diseaseName=d.getName();
        this.categoryName=d.getCategory();
    }
}
