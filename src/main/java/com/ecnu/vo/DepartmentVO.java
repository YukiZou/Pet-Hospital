package com.ecnu.vo;

import com.ecnu.entity.Department;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentVO implements Serializable{
    private int id;
    private String name;
    private int role;
    private String info;
    private String picture;//picture url

    public DepartmentVO() {

    }

    public DepartmentVO(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.role = department.getRole();
        this.info = department.getInfo();
        this.picture = department.getPicture();
    }
}
