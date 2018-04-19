package com.ecnu.service;

import com.ecnu.entity.Facility;

import java.util.List;

/**
 * 设备管理
 * @author asus
 */
public interface FacilityService {

    /**
     * 新增设备
     * @param facility
     * @return
     */
    Boolean addFacility(Facility facility);

    /**
     * 根据参数的id来删除指定的科室记录
     * @param facility
     * @return
     */
    Boolean deleteFacility(Facility facility);

    /**
     * 根据条件查询设备
     * @param facility
     * @return
     */
    List<Facility> queryFacilities(Facility facility);

    /**
     * 根据条件的id list批量查询 Facility 记录
     * @param facilityIds
     * @return
     */
    List<Facility> queryFacilitiesByIds(List<Integer> facilityIds);
}
