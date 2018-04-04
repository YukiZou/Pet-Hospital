package com.ecnu.service;

import com.ecnu.entity.DepDrug;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DepDrugServiceTest extends BaseServiceTest {
    @Resource
    private DepDrugService depDrugService;

    @Test
    public void findDepDrugsByDepId() throws Exception {
        //1.要找的关系不存在
        int depId = 7;
        assertTrue(depDrugService.findDepDrugsByDepId(depId).size() == 0);

        //2.要找的关系存在
        depId = 2;
        assertTrue(depDrugService.findDepDrugsByDepId(depId).size() > 0);
    }

    @Test
    public void findDepDrug() throws Exception {
        List<DepDrug> depDrugList = new ArrayList<>();
        DepDrug depDrug = new DepDrug();
        depDrug.setDepartmentId(1);
        depDrug.setDrugId(3);
        depDrugList.add(depDrug);
        assertTrue(depDrugService.addDepDrugs(depDrugList) > 0);
        assertTrue(depDrugService.findDepDrug(depDrug).size() > 0);
        assertTrue(depDrugService.deleteDepDrugs(depDrug) > 0);
        assertTrue(depDrugService.findDepDrug(depDrug).size() == 0);
    }

    @Test
    public void deleteDepDrugs() throws Exception {
        List<DepDrug> depDrugList = new ArrayList<>();
        assertTrue(depDrugService.addDepDrugs(depDrugList) == 0);
        assertTrue(depDrugService.deleteDepDrugs(depDrugList) == 0);

        depDrugList.add(new DepDrug(3,1));
        depDrugList.add(new DepDrug(4, 2));

        assertTrue(depDrugService.addDepDrugs(depDrugList) > 0);
        assertTrue(depDrugService.deleteDepDrugs(depDrugList) > 0);
        assertTrue(depDrugService.deleteDepDrugs(depDrugList) == 0);
    }

}