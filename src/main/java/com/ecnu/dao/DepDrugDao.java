package com.ecnu.dao;

import com.ecnu.entity.DepDrug;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepDrugDao {

    /**
     * 插入一条科室和药品的关联记录
     * @param depDrug 两个字段drug_id, department_id都需要存在才能插入
     * @return
     */
    int insertDepDrug(DepDrug depDrug);

    /**
     * 删除参数depDrug指定的科室和药品的关联记录
     * @param depDrug 字段drug_id, department_id都需要存在才能删除成功
     * @return
     */
    int deleteDepDrug(DepDrug depDrug);

    /**
     * 根据参数所给的字段检索记录
     * @param depDrug 两字段不必需，可以实现模糊查询；都为空则是检索所有。
     * @return
     */
    List<DepDrug> queryDepDrugs(DepDrug depDrug);

}
