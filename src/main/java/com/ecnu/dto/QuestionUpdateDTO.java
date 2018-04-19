package com.ecnu.dto;

import com.ecnu.entity.Question;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class QuestionUpdateDTO implements Serializable {
    private int id;
    private String category;
    /**
     * 如果题干或选项过大怎么办？
     */
    private String stem;
    private String optA;
    private String optB;
    private String optC;
    private String optD;
    /**
     * 前台传回ABCD
     */
    private String answer;
}
