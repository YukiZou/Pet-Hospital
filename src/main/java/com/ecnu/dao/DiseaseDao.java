package com.ecnu.dao;

import com.ecnu.entity.Disease;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseDao {

    /**
     * 获得病种列表
     * @return
     */
    List<Disease> getAllDisease();

    /**
     * 根据name找到Disease
     * @param name
     * @return
     */
    Disease findDiseaseByName(String name);
    /**
     * 新增病种
     * @param disease
     */
    void insertDisease(Disease disease);
}
