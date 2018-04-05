package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户的做题记录表；关联user和question
 */
@Data
public class Record implements Serializable{
    private int userId;
    private int questionId;
    private String choice;//记录用户的做题选项
    private String TorF;//记录用户做这道题的结果
}
