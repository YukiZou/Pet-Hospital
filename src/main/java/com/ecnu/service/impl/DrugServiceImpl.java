package com.ecnu.service.impl;

import com.ecnu.dao.DrugDao;
import com.ecnu.entity.Drug;
import com.ecnu.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asus
 */
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
    public Boolean deleteDrug(Drug drug) {
        int res = drugDao.deleteDrug(drug);
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

    @Override
    public List<Drug> queryDrugsByIds(List<Integer> drugIds) {
        if (drugIds == null || drugIds.size() == 0) {
            return new ArrayList<>();
        }
        return drugDao.queryDrugsByIds(drugIds);
    }
}
