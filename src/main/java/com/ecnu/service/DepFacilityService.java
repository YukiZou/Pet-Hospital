package com.ecnu.service;

import com.ecnu.entity.DepFacility;

import java.util.List;

/**
 * 科室——设备关联表管理
 * @author asus
 */
public interface DepFacilityService {
    /**
     * 根据depId找到对应的设备关联关系
     * @param DepId
     * @return
     */
    List<DepFacility> findDepFacilitiesByDepId(int DepId);

    /**
     * 根据参数找到对应的关联记录
     * 参数 depFacility 的两个id字段都要存在
     * @param depFacility
     * @return
     */
    List<DepFacility> findDepFacility(DepFacility depFacility);

    /**
     * 根据List型的参数批量增加指定dep_fac表中的记录
     * @param depFacilities
     * @return
     */
    int addDepFacilities(List<DepFacility> depFacilities);

    /**
     * 根据List型的参数批量删除指定dep_fac表中的记录
     * @param depFacilities
     * @return
     */
    int deleteDepFacilities(List<DepFacility> depFacilities);


    /**
     * 删除符合条件的关联表记录
     * @param depFacility
     * @return
     */
    int deleteDepFacilities(DepFacility depFacility);
}
