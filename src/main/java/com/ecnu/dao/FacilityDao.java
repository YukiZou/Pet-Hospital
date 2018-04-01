package com.ecnu.dao;

import com.ecnu.entity.Facility;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityDao {

    /**
     * 新增设备
     * @param facility
     * @return
     */
    int insertFacility(Facility facility);

    /**
     * 通过参数facility的id来删除设备
     * @param facility
     * @return
     */
    int deleteFacility(Facility facility);

    /**
     * 更改facility信息
     * @param facility id用来确定更改哪条记录， 其他字段值是要更改的值
     * @return
     */
    int updateFacility(Facility facility);

    /**
     * 查询设备（感觉只可能通过id来找设备）
     * @param facility
     * @return
     */
    List<Facility> queryFacilities(Facility facility);

    /**
     * 根据设备id查询设备
     * @param id
     * @return
     */
    Facility queryFacilityById(int id);
}
