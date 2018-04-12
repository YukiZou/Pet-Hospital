package com.ecnu.vo;


import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DiseaseCategoryListVO extends BaseResponse implements Serializable {
    private List<String> categoryList;

    public DiseaseCategoryListVO(){

    }

    public DiseaseCategoryListVO(String status) {
        super.setStatus(status);
    }

    public DiseaseCategoryListVO(String status, List<String> categoryList) {
        super.setStatus(status);
        this.categoryList = categoryList;
    }
}
