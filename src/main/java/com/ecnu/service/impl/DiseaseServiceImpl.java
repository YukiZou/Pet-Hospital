package com.ecnu.service.impl;

import com.ecnu.dao.DiseaseDao;
import com.ecnu.entity.Disease;
import com.ecnu.service.DiseaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService {
    private static Logger LOG = LoggerFactory.getLogger(DiseaseServiceImpl.class);

    @Autowired
    private DiseaseDao diseaseDao;

    @Override
    public List<Disease> getAllDiseaseCategory() {
        return diseaseDao.getAllDiseaseCategory();
    }
    @Override
    public List<Disease> queryDiseases(Disease disease) {
        return diseaseDao.queryDiseases(disease);
    }

    @Override
    public Disease findDiseaseById(int id){
        return diseaseDao.findDiseaseById(id);
    }

    @Override
    public Boolean addDisease(Disease disease) {
        String name=disease.getName();
        String category=disease.getCategory();
        if(name == null || name.equals("") || category == null || category.equals("") ){
            LOG.error("新增疾病信息不符合规范，新增疾病失败。");
            return false;
        }
        else if(diseaseDao.findDiseaseByName(name)!=null){//判断新增病种的name是否已在数据库中存在
            LOG.error("新增疾病已存在，新增疾病失败。");
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

    @Override
    public Boolean updateDisease(Disease disease) {
        //判断新修改的病名+category是否已经存在（病名不能重复）
        List<Disease> diseaseList = diseaseDao.findByDisease(disease);
        if(diseaseList != null && diseaseList.size() > 0){
            return false;
        }
        int res = diseaseDao.updateDisease(disease);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteDisease(Disease disease){
        int res = diseaseDao.deleteDisease(disease);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

}
