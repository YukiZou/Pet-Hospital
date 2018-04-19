package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class QuestionQueryDTO implements Serializable {
    private int id;
    private String keyword;
}
