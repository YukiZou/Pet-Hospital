package com.ecnu.dao;

import com.ecnu.entity.Disease;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseDao {

    /**
     * 根据参数disease查询出所有符合查询条件的disease
     * @param disease
     * @return
     */
    List<Disease> queryDiseases(Disease disease);

    /**
     * 列出所有category
     * @param
     * @return
     */
    List<Disease> getAllDiseaseCategory();

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
     * 根据id找到Disease
     * @param id
     * @return
     */
    Disease findDiseaseById(int id);

    /**
     * 新增病种
     * @param disease
     */
    void insertDisease(Disease disease);

    /**
     * 根据参数disease的ID找到要更改的疾病记录，然后update该条记录
     * @param disease
     */
    int updateDisease(Disease disease);
}
