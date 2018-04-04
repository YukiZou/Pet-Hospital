package com.ecnu.controller;

import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.common.response.BaseResponse;
import com.ecnu.dto.*;
import com.ecnu.entity.*;
import com.ecnu.service.*;
import com.ecnu.vo.*;
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

    @Autowired
    private DrugService drugService;

    @Autowired
    private FacilityService facilityService;


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
            depDrug.setDepartmentId(depId);
            depDrugService.deleteDepDrugs(depDrug);

            DepFacility depFacility = new DepFacility();
            depFacility.setDepartmentId(depId);
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

            //除了id以外，其他参数都为空的情况在update时是不合法的
            if ((name == null || name.equals("")) && department.getRole() == 0 && (info == null || info.equals("")) && (department.getPicture() == null || department.getPicture().equals(""))) {
                LOG.error("name/role/info/picture are null");
                return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
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
     * 默认department_id在表中存在，drug_id至少一条，且对应的drug_id在drug表中也是存在的
     * @return
     */
    @RequestMapping(value = "/drug/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addDepDrug(@RequestBody DepDrugDTO depDrugDTO) {
        try {
            LOG.info("start add dep-drug for depDrugDTO {}", depDrugDTO);
            int depId = depDrugDTO.getId();
            List<Integer> drugIds = depDrugDTO.getDrugList();
            //先把传入的参数封装成List<DepDrug>对象
            List<DepDrug> depDrugList = new ArrayList<>();
            for (Integer drugId : drugIds) {
                DepDrug depDrug = new DepDrug();
                depDrug.setDepartmentId(depId);
                depDrug.setDrugId(drugId);
                //检查一下要新增的depDrug是否已经在关系表中存在
                List<DepDrug> queryDepDrug = depDrugService.findDepDrug(depDrug);
                if (queryDepDrug != null && queryDepDrug.size() > 0) {
                    LOG.error("dep-drug relationship for depId {} and drugId {} has already exist", depId, drugId);
                    return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
                }
                depDrugList.add(depDrug);
            }

            int res = depDrugService.addDepDrugs(depDrugList);
            if (res > 0) {
                LOG.info("add dep-drug for depDrugDTO {} success", depDrugDTO);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.info("add dep-drug for depDrugDTO {} failed", depDrugDTO);
                return new BaseResponse(ResponseStatusEnum.SQL_FAIL.getDesc());
            }
        } catch (Exception e) {
            LOG.error("add depDrug failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 批量删除dep-drug关联关系
     * @param depDrugDTO
     * @return
     */
    @RequestMapping(value = "/drug/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteDepDrug(@RequestBody DepDrugDTO depDrugDTO) {
        try {
            LOG.info("start delete dep-drug for depDrugDTO {}", depDrugDTO);
            int depId = depDrugDTO.getId();
            List<Integer> drugIds = depDrugDTO.getDrugList();
            //先把传入的参数封装成List<DepDrug>对象
            List<DepDrug> depDrugList = new ArrayList<>();
            for (Integer drugId : drugIds) {
                DepDrug depDrug = new DepDrug();
                depDrug.setDepartmentId(depId);
                depDrug.setDrugId(drugId);
                depDrugList.add(depDrug);
            }

            int res = depDrugService.deleteDepDrugs(depDrugList);
            if (res > 0) {
                LOG.info("delete dep-drug for depDrugDTO {} success", depDrugDTO);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.info("delete dep-drug for depDrugDTO {} failed", depDrugDTO);
                return new BaseResponse(ResponseStatusEnum.SQL_FAIL.getDesc());
            }
        } catch (Exception e) {
            LOG.error("delete depDrug failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 根据departmentId查询到指定科室对应的药品List
     * @param departmentDeleteDTO
     * @return
     */
    @RequestMapping(value = "/drug/all", method = RequestMethod.POST)
    @ResponseBody
    public DrugListVO queryDrugsByDepId(@RequestBody DepartmentDeleteDTO departmentDeleteDTO) {
        try {
            int depId = departmentDeleteDTO.getId();
            LOG.info("start query all drugs for depId {}", depId);
            //先去关联表中找到DepDrug list
            List<DepDrug> depDrugList = depDrugService.findDepDrugsByDepId(depId);
            //找到对应的drugId list
            List<Integer> drugIds = new ArrayList<>();
            for (DepDrug depDrug : depDrugList) {
                drugIds.add(depDrug.getDrugId());
            }

            //根据drugId List批量查询符合条件的Drug对象
            List<Drug> drugList = drugService.queryDrugsByIds(drugIds);

            List<DrugVO> drugVOList = new ArrayList<>();
            for (Drug d : drugList) {
                DrugVO drugVO = new DrugVO(d);
                drugVOList.add(drugVO);
            }
            LOG.info("query all drugs for depId {} success!", depId);
            return new DrugListVO(ResponseStatusEnum.SUCCESS.getDesc(), drugVOList);
        } catch (Exception e) {
            LOG.error("query drugs failed!");
            e.printStackTrace();
            return new DrugListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 添加指定Department 的 设备-科室关联关系
     * 可一次性添加多条关联关系
     * 默认department_id在表中存在，facility_id至少一条，且对应的facility_id在facility表中也是存在的
     * @return
     */
    @RequestMapping(value = "/facility/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addDepFacility(@RequestBody DepFacilityDTO depFacilityDTO) {
        try {
            LOG.info("start add dep-facility for depFacilityDTO {}", depFacilityDTO);
            int depId = depFacilityDTO.getId();
            List<Integer> facilityIds = depFacilityDTO.getFacilityList();
            //先把传入的参数封装成List<DepFacility>对象
            List<DepFacility> depFacilityList = new ArrayList<>();
            for (Integer facilityId : facilityIds) {
                DepFacility depFacility = new DepFacility();
                depFacility.setDepartmentId(depId);
                depFacility.setFacilityId(facilityId);
                //检查一下要新增的 depFacility 是否已经在关系表中存在
                List<DepFacility> queryDepFacility = depFacilityService.findDepFacility(depFacility);
                if (queryDepFacility != null && queryDepFacility.size() > 0) {
                    LOG.error("dep-facility relationship for depId {} and facilityId {} has already exist", depId, facilityId);
                    return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
                }
                depFacilityList.add(depFacility);
            }

            int res = depFacilityService.addDepFacilities(depFacilityList);
            if (res > 0) {
                LOG.info("add dep-facility for depFacilityDTO {} success", depFacilityDTO);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.info("add dep-drug for depFacilityDTO {} failed", depFacilityDTO);
                return new BaseResponse(ResponseStatusEnum.SQL_FAIL.getDesc());
            }
        } catch (Exception e) {
            LOG.error("add depFacility failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 批量删除dep-facility关联关系
     * @param depFacilityDTO
     * @return
     */
    @RequestMapping(value = "/facility/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteDepFacility(@RequestBody DepFacilityDTO depFacilityDTO) {
        try {
            LOG.info("start delete dep-facility for depFacilityDTO {}", depFacilityDTO);
            int depId = depFacilityDTO.getId();
            List<Integer> facilityIds = depFacilityDTO.getFacilityList();
            //先把传入的参数封装成List<DepFacility>对象
            List<DepFacility> depFacilityList = new ArrayList<>();
            for (Integer facilityId : facilityIds) {
                DepFacility depFacility = new DepFacility();
                depFacility.setFacilityId(facilityId);
                depFacility.setDepartmentId(depId);
                depFacilityList.add(depFacility);
            }

            int res = depFacilityService.deleteDepFacilities(depFacilityList);
            if (res > 0) {
                LOG.info("delete dep-facility for depFacilityDTO {} success", depFacilityDTO);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.info("delete dep-facility for depFacilityDTO {} failed", depFacilityDTO);
                return new BaseResponse(ResponseStatusEnum.SQL_FAIL.getDesc());
            }
        } catch (Exception e) {
            LOG.error("delete depFacility failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 根据departmentId查询到指定科室对应的药品List
     * @param departmentDeleteDTO
     * @return
     */
    @RequestMapping(value = "/facility/all", method = RequestMethod.POST)
    @ResponseBody
    public FacilityListVO queryFacilitiesByDepId(@RequestBody DepartmentDeleteDTO departmentDeleteDTO) {
        try {
            int depId = departmentDeleteDTO.getId();
            LOG.info("start query all facilities for depId {}", depId);
            //先去关联表中找到DepFacility list
            List<DepFacility> depFacilityList = depFacilityService.findDepFacilitiesByDepId(depId);
            //找到对应的facilityId list
            List<Integer> facilityIds = new ArrayList<>();
            for (DepFacility depFacility : depFacilityList) {
                facilityIds.add(depFacility.getFacilityId());
            }
            //根据facilityId list批量查询符合条件的 Facility 对象
            List<Facility> facilityList = facilityService.queryFacilitiesByIds(facilityIds);

            List<FacilityVO> facilityVOList = new ArrayList<>();
            for (Facility f : facilityList) {
                FacilityVO facilityVO = new FacilityVO(f);
                facilityVOList.add(facilityVO);
            }
            LOG.info("query all facilities for depId {} success!", depId);
            return new FacilityListVO(ResponseStatusEnum.SUCCESS.getDesc(), facilityVOList);
        } catch (Exception e) {
            LOG.error("query facilities failed!");
            e.printStackTrace();
            return new FacilityListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

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
