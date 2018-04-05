package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Question;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增试题方法返回的对象
 */
@Data
public class QuestionVO extends BaseResponse implements Serializable {
    private int id;
    private String category;
    private String stem;
    private String optA;
    private String optB;
    private String optC;
    private String optD;
    private String answer;

    public QuestionVO() {

    }

    public QuestionVO(String status) {
        super.setStatus(status);
    }

    public QuestionVO(Question question) {
        this.id = question.getId();
        this.category=question.getCategory();
        this.stem=question.getStem();
        this.optA=question.getOptA();
        this.optB=question.getOptB();
        this.optC=question.getOptC();
        this.optD=question.getOptD();
        this.answer=question.getAnswer();
    }
}
