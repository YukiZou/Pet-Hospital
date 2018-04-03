package com.ecnu.controller;

import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.common.response.BaseResponse;
import com.ecnu.dto.DepartmentAddDTO;
import com.ecnu.dto.DepartmentDeleteDTO;
import com.ecnu.dto.DepartmentQueryDTO;
import com.ecnu.entity.DepDrug;
import com.ecnu.entity.DepFacility;
import com.ecnu.entity.Department;
import com.ecnu.service.DepDrugService;
import com.ecnu.service.DepFacilityService;
import com.ecnu.service.DepartmentService;
import com.ecnu.vo.DepartmentAddVO;
import com.ecnu.vo.DepartmentListVO;
import com.ecnu.vo.DepartmentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.List;

import static com.ecnu.common.CheckInputStringUtil.containIllegalCharacter;

/**
 * 科室管理
 */
@Controller
@RequestMapping("api/department")
public class DepartmentController {
    private static Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepDrugService depDrugService;
    @Autowired
    private DepFacilityService depFacilityService;

    /**
     * 新增科室
     * 限制：科室名 name 唯一且不为 null
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public DepartmentAddVO addDepartment(@RequestBody DepartmentAddDTO departmentAddDTO) {
        try {
            LOG.info("start add department for departmentAddDTO: {}", departmentAddDTO);
            String name = departmentAddDTO.getName();
            String info = departmentAddDTO.getInfo();
            Department department = toDepartment(departmentAddDTO);

            //如果将要新增的 department 的name已经存在在表 department 中，则新增不成功。
            Department queryDepartment = new Department();
            queryDepartment.setName(name);
            List<Department> departmentList = departmentService.queryDepartments(queryDepartment);
            if (departmentList.size() > 0) {
                LOG.error("the department name is already exist or name is null, add department failed!");
                return new DepartmentAddVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

            if ((name != null && containIllegalCharacter(name)) || (info != null && containIllegalCharacter(info))) {
                LOG.error("department name or info are not invalid!");
                return new DepartmentAddVO(ResponseStatusEnum.INVALID_INPUT_FAIL.getDesc());
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

    /**
     * 删除指定Id的科室，并且把关联表中与此科室关联的记录全部删掉
     * @param departmentDeleteDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public BaseResponse deleteDepartment(@RequestBody DepartmentDeleteDTO departmentDeleteDTO) {
        try {
            int depId = departmentDeleteDTO.getId();
            LOG.info("start delete department for id {}", depId);
            if (depId <= 0) {
                LOG.error("invalid department id");
                return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }
            //List<DepDrug> depDrugList = depDrugService.findDepDrugsByDepId(depId);
            //List<DepFacility> depFacilityList = depFacilityService.findDepFacilitiesByDepId(depId);
            //删除科室，首先删除和该科室关联的药物和设备记录。
            DepDrug depDrug = new DepDrug();
            depDrug.setDepartment_id(depId);
            depDrugService.deleteDepDrugs(depDrug);

            DepFacility depFacility = new DepFacility();
            depFacility.setDepartment_id(depId);
            depFacilityService.deleteDepFacilities(depFacility);

            Department department = new Department();
            department.setId(depId);
            departmentService.deleteDepartment(department);

            LOG.info("delete department for id {} success", depId);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());

        } catch (Exception e) {
            LOG.error("delete department for id {} failed!", departmentDeleteDTO.getId());
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }


    /**
     * 根据参数的数据值更新数据库中的记录
     * 前置条件：department id 字段一定有且存在在数据库中，其他字段确保有一个字段有要更改的新值
     * @param department
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateDepartment(@RequestBody Department department) {
        try {
            LOG.info("start update department data for {}", department);
            int depId = department.getId();//id是一定有的
            String name = department.getName();
            String info = department.getInfo();
            if (depId <= 0) {
                LOG.error("department id does not exist!");
                return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

            if ((name != null && containIllegalCharacter(name)) || (info != null && containIllegalCharacter(info))) {
                LOG.error("department name or info are not invalid for update department data!");
                return new BaseResponse(ResponseStatusEnum.INVALID_INPUT_FAIL.getDesc());
            }

            departmentService.updateDepartment(department);
            LOG.info("update department data for {} success", department);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());

        } catch (Exception e) {
            LOG.error("update department data for {} failed!", department);
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }


    /**
     * 根据参数查询科室列表
     * 前置条件：参数id/name可以同时存在或者同时不存在，如果同时不存在则是查所有科室
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public DepartmentListVO queryDepartment(@RequestBody DepartmentQueryDTO departmentQueryDTO) {
        try {
            LOG.info("start query department list for filter {}", departmentQueryDTO);
            Department department = toDepartment(departmentQueryDTO);
            List<Department> departmentList = departmentService.queryDepartments(department);

            List<DepartmentVO> departmentVOList = new ArrayList<>();
            for (Department dep : departmentList) {
                DepartmentVO departmentVO = new DepartmentVO(dep);
                departmentVOList.add(departmentVO);
            }

            LOG.info("query department list for filter {} success!", departmentQueryDTO);
            return new DepartmentListVO(ResponseStatusEnum.SUCCESS.getDesc(), departmentVOList);

        } catch (Exception e) {
            LOG.error("query department list for filter {} failed!", departmentQueryDTO);
            e.printStackTrace();
            return new DepartmentListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }


    /**
     * 添加指定Department 的 药品-科室关联关系
     * 可一次性添加多条关联关系
     * @return
     */
    /*@RequestMapping(value = "/drug/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addDepDrug() {
        try {

        } catch (Exception e) {
            LOG.error("add department failed!");
            e.printStackTrace();
            return new DepartmentAddVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }*/

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

    private Department toDepartment(DepartmentQueryDTO departmentQueryDTO) {
        Department department = new Department();
        department.setId(departmentQueryDTO.getId());
        department.setName(departmentQueryDTO.getName());
        return department;
    }
}
