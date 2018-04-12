package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentAddDTO implements Serializable {
    private String name;//科室名，如诊室、档案室、免疫室等。必须有，不可重复
    private int role;//1:前台； 2：医助； 3：兽医。前台展示下拉框选择。
    private String info; //科室功能简介
    private String picture;//科室图片，可无
}
