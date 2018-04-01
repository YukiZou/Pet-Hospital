package com.ecnu.controller;


import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.dto.FacilityAddDTO;
import com.ecnu.dto.FacilityQueryDTO;
import com.ecnu.entity.Facility;
import com.ecnu.service.FacilityService;
import com.ecnu.vo.FacilityAddVO;
import com.ecnu.vo.FacilityListVO;
import com.ecnu.vo.FacilityVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/facility")
public class FacilityController {
    private static Logger LOG = LoggerFactory.getLogger(FacilityController.class);

    @Autowired
    private FacilityService facilityService;

    /**
     * 新增设备的方法，比较完善，针对不同的错误返回了不同的错误类型
     * @param facilityAddDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public FacilityAddVO addDrug(@RequestBody FacilityAddDTO facilityAddDTO) {
        try {
            LOG.info("start add facility for facilityAddDTO: {}", facilityAddDTO);
            //将新增的 facility 信息转换成 Facility 对象
            Facility facility = toFacility(facilityAddDTO);

            //如果将要新增的 facility 的name已经存在在表 facility 中，则新增不成功。
            Facility queryFacility = new Facility();
            queryFacility.setName(facility.getName());
            List<Facility> facilityList = facilityService.queryFacilities(queryFacility);
            if (facilityList.size() > 0) {
                LOG.error("the facility name is already exist or name is null, add facility failed!");
                return new FacilityAddVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

            Boolean res = facilityService.addFacility(facility);
            FacilityAddVO facilityAddVO = new FacilityAddVO(facility);//将新增后得到的最新 facility 返回给前端

            if (res) {
                LOG.info("add facility success. Complete facility add.");
                facilityAddVO.setStatus(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("sql execute error, add facility failed!");
                facilityAddVO.setStatus(ResponseStatusEnum.SQL_FAIL.getDesc());
            }

            return facilityAddVO;

        } catch (Exception e) {
            LOG.error("add drug failed!");
            e.printStackTrace();
            return new FacilityAddVO(ResponseStatusEnum.FAIL.getDesc());
        }

    }


    /**
     * 查询数据库表 facility 中所有的设备记录
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public FacilityListVO allDrugs() {
        try {
            LOG.info("start query all facilities!");
            List<Facility> facilityList = facilityService.queryFacilities(new Facility());
            List<FacilityVO> facilityVOList = new ArrayList<FacilityVO>();
            //将格式转化
            for (Facility facility : facilityList) {
                FacilityVO facilityVO = new FacilityVO(facility);
                facilityVOList.add(facilityVO);
            }
            LOG.info("facilityVO list: {}", facilityVOList);
            LOG.info("query all facilities success!");
            return new FacilityListVO(ResponseStatusEnum.SUCCESS.getDesc(), facilityVOList);

        } catch (Exception e) {
            LOG.error("query all facilities failed!");
            e.printStackTrace();
            return new FacilityListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }


    /**
     * 根据条件查询符合条件的设备，模糊查询
     * @param facilityQueryDTO
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public FacilityListVO queryDrugs(@RequestBody FacilityQueryDTO facilityQueryDTO) {
        try {
            LOG.info("start query facilities for filter {} !", facilityQueryDTO);
            //将 FacilityQueryDTO 对象转换成 Facility 对象
            Facility facility = toFacility(facilityQueryDTO);

            List<Facility> facilityList = facilityService.queryFacilities(facility);
            List<FacilityVO> facilityVOList = new ArrayList<FacilityVO>();
            //将格式转化
            for (Facility f : facilityList) {
                FacilityVO facilityVO = new FacilityVO(f);
                facilityVOList.add(facilityVO);
            }
            LOG.info("facilityVO list: {}", facilityVOList);
            LOG.info("query facilities for filter {} success!", facilityQueryDTO);
            return new FacilityListVO(ResponseStatusEnum.SUCCESS.getDesc(), facilityVOList);

        } catch (Exception e) {
            LOG.error("query facilities for filter {} failed!", facilityQueryDTO);
            e.printStackTrace();
            return new FacilityListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }


    /**
     * FacilityAddDTO 对象转化成 Facility 对象方法
     * @param facilityAddDTO
     * @return
     */
    private Facility toFacility(FacilityAddDTO facilityAddDTO) {
        Facility facility = new Facility();
        facility.setName(facilityAddDTO.getName());
        facility.setInfo(facilityAddDTO.getInfo());
        facility.setPicture(facilityAddDTO.getPicture());
        return facility;
    }

    /**
     * FacilityQueryDTO 对象转化成 Facility对象方法
     * @param facilityQueryDTO
     * @return
     */
    private Facility toFacility(FacilityQueryDTO facilityQueryDTO) {
        Facility facility = new Facility();
        facility.setName(facilityQueryDTO.getName());
        return facility;
    }

}
