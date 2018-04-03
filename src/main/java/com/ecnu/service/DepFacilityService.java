package com.ecnu.service;

import com.ecnu.entity.DepFacility;

import java.util.List;

/**
 * 科室——设备关联表管理
 */
public interface DepFacilityService {
    /**
     * 根据depId找到对应的设备关联关系
     * @param DepId
     * @return
     */
    List<DepFacility> findDepFacilitiesByDepId(int DepId);

    /**
     * 删除符合条件的关联表记录
     * @param depFacility
     * @return
     */
    void deleteDepFacilities(DepFacility depFacility);
}
