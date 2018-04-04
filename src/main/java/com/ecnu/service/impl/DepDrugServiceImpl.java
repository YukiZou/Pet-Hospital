package com.ecnu.service.impl;

import com.ecnu.dao.DepDrugDao;
import com.ecnu.entity.DepDrug;
import com.ecnu.service.DepDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepDrugServiceImpl implements DepDrugService {
    @Autowired
    private DepDrugDao depDrugDao;

    @Override
    public List<DepDrug> findDepDrugsByDepId(int depId) {
        DepDrug depDrug = new DepDrug();
        depDrug.setDepartmentId(depId);
        return depDrugDao.queryDepDrugs(depDrug);
    }

    @Override
    public List<DepDrug> findDepDrug(DepDrug depDrug) {
        return depDrugDao.queryDepDrugs(depDrug);
    }

    @Override
    public int addDepDrugs(List<DepDrug> depDrugs) {
        if (depDrugs == null || depDrugs.size() == 0) {
            return 0;
        }
        return depDrugDao.insertDepDrugs(depDrugs);
    }

    @Override
    public int deleteDepDrugs(List<DepDrug> depDrugs) {
        if (depDrugs == null || depDrugs.size() == 0) {
            return 0;
        }
        return depDrugDao.deleteDepDrugs(depDrugs);
    }

    @Override
    public int deleteDepDrugs(DepDrug depDrug) {
        return depDrugDao.deleteDepDrug(depDrug);
    }
}
