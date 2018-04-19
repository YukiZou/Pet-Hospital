package com.ecnu.dao;


import com.ecnu.entity.Drug;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author asus
 */
@Repository
public interface DrugDao {

    /**
     * 新增药品
     * @param drug
     * @return
     */
    int insertDrug(Drug drug);

    /**
     * 通过参数drug的id来删除药品
     * @param drug
     * @return
     */
    int deleteDrug(Drug drug);

    /**
     * 更改Drug信息
     * @param drug id用来确定更改哪条记录， 其他字段值是要更改的值
     * @return
     */
    int updateDrug(Drug drug);

    /**
     * 查询药品
     * @param drug
     * @return
     */
    List<Drug> queryDrugs(Drug drug);

    /**
     * 根据药品id找到对应的drug记录
     * @param id
     * @return
     */
    Drug queryDrugById(int id);

    /**
     * 根据id list批量查询 drug 记录
     * @param drugIds
     * @return
     */
    List<Drug> queryDrugsByIds(List<Integer> drugIds);
}
