package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class DepartmentAddDTO implements Serializable {
    /**
     * 科室名，如诊室、档案室、免疫室等。必须有，不可重复
     */
    private String name;

    /**
     * 1:前台； 2：医助； 3：兽医。前台展示下拉框选择。
     */
    private int role;

    /**
     * 科室功能简介
     */
    private String info;

    /**
     * 科室图片，可无
     */
    private String picture;
}
