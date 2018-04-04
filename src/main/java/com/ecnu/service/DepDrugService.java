package com.ecnu.service;

import com.ecnu.entity.DepDrug;

import java.util.List;

/**
 * 科室——药品 关联关系管理
 */
public interface DepDrugService {
    /**
     * 根据depId找到关联记录
     * @param depId
     * @return
     */
    List<DepDrug> findDepDrugsByDepId(int depId);

    /**
     * 根据参数找到对应的关联记录
     * 参数depDrug的两个id字段都要存在
     * @param depDrug
     * @return
     */
    List<DepDrug> findDepDrug(DepDrug depDrug);

    /**
     * 根据List型的参数批量增加指定dep_dru表中的记录
     * @param depDrugs
     * @return
     */
    int addDepDrugs(List<DepDrug> depDrugs);

    /**
     * 根据List型的参数批量删除指定dep_dru表中的记录
     * @param depDrugs
     * @return
     */
    int deleteDepDrugs(List<DepDrug> depDrugs);

    /**
     * 根据参数删除指定的关联记录
     * @param depDrug
     */
    void deleteDepDrugs(DepDrug depDrug);
}
