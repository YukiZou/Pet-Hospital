package com.ecnu.service;

import com.ecnu.entity.Disease;

import java.util.List;

public interface DiseaseService {


    List<Disease> getAllDiseaseCategory();

    Disease findDiseaseById(int id);

    /**
     * 根据参数disease的数据来查找符合条件的disease记录
     * @param disease
     * @return
     */
    List<Disease> queryDiseases(Disease disease);

    /**
     * 新增疾病
     * @param disease
     */
    Boolean addDisease(Disease disease);


    /**
     * 修改疾病
     * @param disease
     * @return
     */
    Boolean updateDisease(Disease disease);
}
