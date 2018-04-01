package com.ecnu.service;

import com.ecnu.entity.Department;

import java.util.List;

/**
 * 科室管理
 */
public interface DepartmentService {

    /**
     * 新增一个科室
     * @param department
     * @return
     */
    Boolean addDepartment(Department department);

    /**
     * 根据条件查询科室列表
     * @param department
     * @return
     */
    List<Department> queryDepartments(Department department);
}
