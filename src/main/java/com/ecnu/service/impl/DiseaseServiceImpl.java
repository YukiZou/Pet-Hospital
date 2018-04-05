package com.ecnu.service.impl;

import com.ecnu.dao.DiseaseDao;
import com.ecnu.entity.Disease;
import com.ecnu.service.DiseaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseServiceImpl implements DiseaseService {
    private static Logger LOG = LoggerFactory.getLogger(DiseaseServiceImpl.class);

    @Autowired
    private DiseaseDao diseaseDao;

    @Override
    public Boolean addDisease(Disease disease) {
        String name=disease.getName();
        String category=disease.getCategory();
        if(name == null || name.equals("") || category == null || category.equals("") ){
            LOG.error("新增病种信息不符合规范，新增病种失败。");
            return false;
        }
        else if(diseaseDao.findDiseaseByName(name)!=null){//判断新增病种的name是否已在数据库中存在
            LOG.error("新增病种已存在，新增病种失败。");
            return false;
        }
        else{

            diseaseDao.insertDisease(disease);
            if (disease.getId() > 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}
