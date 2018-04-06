package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProcedureAddDTO implements Serializable {
    private int roleId;//1 2 3
    private String domain;//流程的步骤的名字
    private int step;//1 2 3 表示第几步
    private String stepName;//step的名字
    private String info;
    private List<String> pictures; //存放 picture url的数组
    private List<String> videos; //存放 video url的数组
}
