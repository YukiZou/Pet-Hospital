package com.ecnu.vo;

import com.ecnu.entity.Department;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class DepartmentVO implements Serializable{
    private int id;
    private String name;
    private int role;
    private String info;

    /**
     * picture url
     */
    private String picture;

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
