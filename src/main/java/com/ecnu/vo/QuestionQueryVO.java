package com.ecnu.vo;

import com.ecnu.entity.Question;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionQueryVO implements Serializable {
    private int id;
    private String category;
    private String stem;
    private String optA;
    private String optB;
    private String optC;
    private String optD;
    private String answer;

    public QuestionQueryVO() {

    }

    public QuestionQueryVO(Question question) {
        this.id=question.getId();
        this.category=question.getCategory();
        this.stem=question.getStem();
        this.optA=question.getOptA();
        this.optB=question.getOptB();
        this.optC=question.getOptC();
        this.optD=question.getOptD();
        this.answer=question.getAnswer();
    }
}
