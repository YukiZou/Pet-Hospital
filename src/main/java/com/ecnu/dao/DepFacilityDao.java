package com.ecnu.dao;

import com.ecnu.entity.DepFacility;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author asus
 */
@Repository
public interface DepFacilityDao {

    /**
     * 插入一条科室和设备的关联记录
     * @param depFacility 两个字段facility_id, department_id都需要存在才能插入
     * @return
     */
    int insertDepFacility(DepFacility depFacility);

    /**
     * 批量插入记录
     * @param depFacilities
     * @return
     */
    int insertDepFacilities(List<DepFacility> depFacilities);

    /**
     * 删除参数depFacility指定的科室和设备的关联记录
     * @param depFacility 字段facility_id, department_id都需要存在才能删除成功
     * @return
     */
    int deleteDepFacility(DepFacility depFacility);

    /**
     * 批量删除记录
     * @param depFacilities
     * @return
     */
    int deleteDepFacilities(List<DepFacility> depFacilities);

    /**
     * 根据参数所给的字段检索记录
     * @param depFacility 两字段不必需，可以实现模糊查询；都为空则是检索所有。
     * @return
     */
    List<DepFacility> queryDepFacilities(DepFacility depFacility);

}
