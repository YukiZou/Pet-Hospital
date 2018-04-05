package com.ecnu.service;

import com.ecnu.entity.Disease;
import com.ecnu.entity.User;

public interface DiseaseService {


    /**
     * 新增病种
     * @param disease
     */
    Boolean addDisease(Disease disease);
}
