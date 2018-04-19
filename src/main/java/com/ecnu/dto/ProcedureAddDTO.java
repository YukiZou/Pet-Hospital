package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asus
 */
@Data
public class ProcedureAddDTO implements Serializable {
    /**
     * 1 2 3
     */
    private int roleId;
    /**
     * 流程的步骤的名字
     */
    private String domain;
    /**
     * 1 2 3 表示第几步
     */
    private int step;
    /**
     * step的名字
     */
    private String stepName;
    private String info;
    /**
     * 存放 picture url的数组
     */
    private List<String> pictures;
    /**
     * 存放 video url的数组
     */
    private List<String> videos;
}
