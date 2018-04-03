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
     * 根据参数删除指定的关联记录
     * @param depDrug
     */
    void deleteDepDrugs(DepDrug depDrug);
}
