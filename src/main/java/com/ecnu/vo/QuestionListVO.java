package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionListVO extends BaseResponse implements Serializable {
    private List<QuestionQueryVO> questionList;

    public QuestionListVO(){

    }

    public QuestionListVO(String status) {
        super.setStatus(status);
    }

    public QuestionListVO(String status, List<QuestionQueryVO> questionQueryVOList) {
        super.setStatus(status);
        this.questionList = questionQueryVOList;
    }
}
