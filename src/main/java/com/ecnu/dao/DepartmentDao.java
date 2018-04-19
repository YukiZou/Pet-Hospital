package com.ecnu.dao;

import com.ecnu.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author asus
 */
@Repository
public interface DepartmentDao {

    /**
     * 新增科室
     * @param department
     * @return
     */
    int insertDepartment(Department department);

    /**
     * 通过参数department的id来指定删除的记录
     * @param department
     * @return
     */
    int deleteDepartment(Department department);

    /**
     * 更改参数department的id指定的记录的其他字段数据
     * @param department
     * @return
     */
    int updateDepartment(Department department);

    /**
     * 通过参数department的各字段模糊查找
     * @param department
     * @return
     */
    List<Department> queryDepartments(Department department);

    /**
     * 通过参数id找对应的Department记录
     * @param id
     * @return
     */
    Department queryDepartmentById(int id);
}
