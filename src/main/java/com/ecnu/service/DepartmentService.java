package com.ecnu.service;

import com.ecnu.entity.Department;

import java.util.List;

/**
 * 科室管理
 * @author asus
 */
public interface DepartmentService {

    /**
     * 新增一个科室
     * @param department
     * @return
     */
    Boolean addDepartment(Department department);

    /**
     * 删除指定科室
     * 限制：参数department 的 id 字段一定要存在
     * @param department
     * @return
     */
    Boolean deleteDepartment(Department department);

    /**
     * 更改指定科室
     * 限制： 参数 department 的 id 字段一定要有
     * @param department
     * @return
     */
    Boolean updateDepartment(Department department);

    /**
     * 根据条件查询科室列表
     * @param department
     * @return
     */
    List<Department> queryDepartments(Department department);
}
