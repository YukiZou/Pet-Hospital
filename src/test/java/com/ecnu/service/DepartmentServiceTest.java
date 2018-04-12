package com.ecnu.service;

import com.ecnu.entity.Department;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class DepartmentServiceTest extends BaseServiceTest{
    @Resource
    private DepartmentService departmentService;

    @Test
    public void addDepartment() throws Exception {
        String name = "testAdd" + System.currentTimeMillis();
        Department department = new Department();
        department.setName(name);
        assertTrue(departmentService.addDepartment(department));
        assertTrue(departmentService.deleteDepartment(department));
    }

    @Test
    public void deleteDepartment() throws Exception {
        String name = "testDelete" + System.currentTimeMillis();
        Department department = new Department();
        department.setName(name);
        assertTrue(departmentService.addDepartment(department));
        assertTrue(departmentService.deleteDepartment(department));
        assertFalse(departmentService.deleteDepartment(department));
    }

    @Test
    public void updateDepartment() throws Exception {
        String name = "testUpdate" + System.currentTimeMillis();
        Department department = new Department();
        department.setName(name);
        assertTrue(departmentService.addDepartment(department));
        department.setRole(3);
        assertTrue(departmentService.updateDepartment(department));
        assertTrue(departmentService.deleteDepartment(department));
        department.setPicture("testUrl");
        assertFalse(departmentService.updateDepartment(department));
    }

    @Test
    public void queryDepartments() throws Exception {
        String name = "testQuery" + System.currentTimeMillis();
        Department department = new Department();
        department.setName(name);
        assertTrue(departmentService.addDepartment(department));
        Department queryDep = new Department();
        queryDep.setName("testQuery");
        assertTrue(departmentService.queryDepartments(queryDep).size() > 0);
        assertTrue(departmentService.deleteDepartment(department));
        System.out.println(departmentService.queryDepartments(queryDep));
        assertTrue(departmentService.queryDepartments(queryDep).size() == 0);
    }

}