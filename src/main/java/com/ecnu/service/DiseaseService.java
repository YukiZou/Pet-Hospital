package com.ecnu.service;

import com.ecnu.entity.Disease;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @author asus
 */
public interface DiseaseService {
    /**
     * 得到所有的disease 的种类
     * @return
     */
    List<Disease> getAllDiseaseCategory();

    /**
     * 根据id找 disease
     * @param id
     * @return
     */
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
     * @return
     */
    Boolean addDisease(Disease disease);


    /**
     * 修改疾病
     * @param disease
     * @return
     */
    Boolean updateDisease(Disease disease);

    /**
     * 删除疾病
     * @param disease
     * @return
     */
    Boolean deleteDisease(Disease disease);
}
