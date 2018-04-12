package com.ecnu.dto;

import com.ecnu.entity.Question;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionDTO implements Serializable {
    private String category;
    private String stem;//如果题干或选项过大怎么办？
    private String optA;
    private String optB;
    private String optC;
    private String optD;
    private String answer;//前台传回ABCD

    public QuestionDTO(){

    }

    public QuestionDTO(Question question){
        this.category=question.getCategory();
        this.stem=question.getStem();
        this.optA =question.getOptA();
        this.optB =question.getOptB();
        this.optC =question.getOptC();
        this.optD =question.getOptD();
        this.answer=question.getAnswer();

    }
}
