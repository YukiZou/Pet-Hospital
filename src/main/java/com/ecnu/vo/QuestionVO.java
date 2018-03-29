package com.ecnu.vo;

import com.ecnu.common.response.BaseResponse;
import com.ecnu.entity.Question;

import java.io.Serializable;

/**
 * 新增试题方法返回的对象
 */
public class QuestionVO extends BaseResponse implements Serializable {
    private int id;
    private String category;
    private String stem;
    private String A;
    private String B;
    private String C;
    private String D;
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
        this.A=question.getA();
        this.B=question.getB();
        this.C=question.getC();
        this.D=question.getD();
        this.answer=question.getAnswer();
    }
}
