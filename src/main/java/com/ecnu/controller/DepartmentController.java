package com.ecnu.controller;

import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.dto.DepartmentAddDTO;
import com.ecnu.entity.Department;
import com.ecnu.service.DepartmentService;
import com.ecnu.vo.DepartmentAddVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 科室管理
 */
@Controller
@RequestMapping("api/department")
public class DepartmentController {
    private static Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    /**
     * 新增科室
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public DepartmentAddVO addDepartment(@RequestBody DepartmentAddDTO departmentAddDTO) {
        try {
            LOG.info("start add department for departmentAddDTO: {}", departmentAddDTO);
            Department department = toDepartment(departmentAddDTO);

            //如果将要新增的 department 的name已经存在在表 department 中，则新增不成功。
            Department queryDepartment = new Department();
            queryDepartment.setName(department.getName());
            List<Department> departmentList = departmentService.queryDepartments(queryDepartment);
            if (departmentList.size() > 0) {
                LOG.error("the department name is already exist or name is null, add department failed!");
                return new DepartmentAddVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

            Boolean res = departmentService.addDepartment(department);
            DepartmentAddVO departmentAddVO = new DepartmentAddVO(department);

            if (res) {
                LOG.info("add department success. Complete department add.");
                departmentAddVO.setStatus(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("sql execute error, add department failed!");
                departmentAddVO.setStatus(ResponseStatusEnum.SQL_FAIL.getDesc());
            }

            return departmentAddVO;

        } catch (Exception e) {
            LOG.error("add department failed!");
            e.printStackTrace();
            return new DepartmentAddVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /*try {

    } catch (Exception e) {
        LOG.error("add department failed!");
        e.printStackTrace();
        return new DepartmentAddVO(ResponseStatusEnum.FAIL.getDesc());
    }*/

    /**
     * 将 DepartmentAddDTO 对象转化成 Department 对象
     * @param departmentAddDTO
     * @return
     */
    private Department toDepartment(DepartmentAddDTO departmentAddDTO) {
        Department department = new Department();
        department.setName(departmentAddDTO.getName());
        department.setRole(departmentAddDTO.getRole());
        department.setInfo(departmentAddDTO.getInfo());
        department.setPicture(departmentAddDTO.getPicture());
        return department;
    }

}
