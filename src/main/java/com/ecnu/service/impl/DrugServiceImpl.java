package com.ecnu.service.impl;

import com.ecnu.dao.DrugDao;
import com.ecnu.entity.Drug;
import com.ecnu.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugServiceImpl implements DrugService{
    @Autowired
    private DrugDao drugDao;

    @Override
    public Boolean addDrug(Drug drug) {
        int res = drugDao.insertDrug(drug);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Drug> queryDrugs(Drug drug) {
        return drugDao.queryDrugs(drug);
    }
}
