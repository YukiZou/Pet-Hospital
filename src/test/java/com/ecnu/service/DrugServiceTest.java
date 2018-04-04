package com.ecnu.service;

import com.ecnu.entity.Drug;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DrugServiceTest extends BaseServiceTest {
    @Resource
    private DrugService drugService;

    @Test
    public void addDrug() throws Exception {
        String name = "testAdd" + System.currentTimeMillis();
        Drug drug = new Drug();
        drug.setName(name);
        assertTrue(drugService.addDrug(drug));
        assertTrue(drugService.deleteDrug(drug));
    }

    @Test
    public void deleteDrug() throws Exception {
        String name = "testDelete" + System.currentTimeMillis();
        Drug drug = new Drug();
        drug.setName(name);
        assertTrue(drugService.addDrug(drug));
        assertTrue(drugService.deleteDrug(drug));
        assertFalse(drugService.deleteDrug(drug));
    }

    @Test
    public void queryDrugs() throws Exception {
        String name = "testQuery" + System.currentTimeMillis();
        Drug drug = new Drug();
        drug.setName(name);
        assertTrue(drugService.addDrug(drug));
        Drug queryDrug = new Drug();
        queryDrug.setName("testQuery");
        assertTrue(drugService.queryDrugs(queryDrug).size() > 0);
        assertTrue(drugService.deleteDrug(drug));
        assertTrue(drugService.queryDrugs(queryDrug).size() == 0);
    }

    @Test
    public void queryDrugsByIds() throws Exception {
        List<Integer> ids = new ArrayList<>();
        assertTrue(drugService.queryDrugsByIds(ids).size() == 0);

        String name1 = "testQuery1" + System.currentTimeMillis();
        String name2 = "testQuery2" + System.currentTimeMillis();
        Drug drug1 = new Drug();
        drug1.setName(name1);
        assertTrue(drugService.addDrug(drug1));
        Drug drug2 = new Drug();
        drug1.setName(name2);
        assertTrue(drugService.addDrug(drug2));

        ids.add(drug1.getId());
        ids.add(drug2.getId());

        assertTrue(drugService.queryDrugsByIds(ids).size() > 0);
        assertTrue(drugService.deleteDrug(drug1));
        assertTrue(drugService.deleteDrug(drug2));
        assertTrue(drugService.queryDrugsByIds(ids).size() == 0);
    }

}