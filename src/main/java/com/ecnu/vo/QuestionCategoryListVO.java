package com.ecnu.vo;

import com.ecnu.common.response.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionCategoryListVO extends BaseResponse implements Serializable {
    private List<String> categoryList;

    public QuestionCategoryListVO(){

    }

    public QuestionCategoryListVO(String status) {
        super.setStatus(status);
    }

    public QuestionCategoryListVO(String status, List<String> categoryList) {
        super.setStatus(status);
        this.categoryList = categoryList;
    }
}
