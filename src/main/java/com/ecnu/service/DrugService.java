package com.ecnu.service;

import com.ecnu.entity.Drug;

import java.util.List;

/**
 * 药品管理服务
 */
public interface DrugService {

    /**
     * 新增药品
     * @param drug
     * @return
     */
    Boolean addDrug(Drug drug);

    /**
     * 根据条件查询药品
     * @param drug
     * @return
     */
    List<Drug> queryDrugs(Drug drug);
}
