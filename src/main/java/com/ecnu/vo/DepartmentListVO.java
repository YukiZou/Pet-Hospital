package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asus
 */
@Data
public class DepartmentListVO extends BaseResponse implements Serializable{
    private List<DepartmentVO> departmentList;

    public DepartmentListVO() {

    }

    public DepartmentListVO(String status) {
        super.setStatus(status);
    }

    public DepartmentListVO(String status, List<DepartmentVO> departmentList) {
        super.setStatus(status);
        this.departmentList = departmentList;
    }
}
