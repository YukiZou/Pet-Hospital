package com.ecnu.service.impl;

import com.ecnu.dao.DepartmentDao;
import com.ecnu.entity.Department;
import com.ecnu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author asus
 */
@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public Boolean addDepartment(Department department) {
        int res = departmentDao.insertDepartment(department);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteDepartment(Department department) {
        int res = departmentDao.deleteDepartment(department);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateDepartment(Department department) {
        int res = departmentDao.updateDepartment(department);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Department> queryDepartments(Department department) {
        return departmentDao.queryDepartments(department);
    }
}
