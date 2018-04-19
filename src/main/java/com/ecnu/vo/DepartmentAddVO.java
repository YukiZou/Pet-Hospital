package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Department;
import lombok.Data;

import java.io.Serializable;

/**
 * the return data for add department method
 * @author asus
 */
@Data
public class DepartmentAddVO extends BaseResponse implements Serializable {
    private Integer id;
    private String name;
    private Integer role;
    private String info;
    /**
     * picture url
     */
    private String picture;

    public DepartmentAddVO() {

    }

    public DepartmentAddVO(String status) {
        super.setStatus(status);
    }

    public DepartmentAddVO(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.role = department.getRole();
        this.info = department.getInfo();
        this.picture = department.getPicture();
    }
}
