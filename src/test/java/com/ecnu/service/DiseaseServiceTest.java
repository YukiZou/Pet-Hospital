package com.ecnu.service;

import com.ecnu.entity.Disease;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class DiseaseServiceTest extends BaseServiceTest {

    @Resource
    private DiseaseService diseaseService;

    @Test
    public void getAllDiseaseCategory() {
        assertTrue(diseaseService.getAllDiseaseCategory().size() > 0);

    }

    @Test
    public void findDiseaseById() {
        //该id存在的情况
        Disease d = diseaseService.findDiseaseById(1);
        assertTrue(d.getId() > 0);

        //该id不存在的情况
        Disease dd = diseaseService.findDiseaseById(2);
        assertTrue(dd == null);
    }

    @Test
    public void queryDiseases() {
        //queryDiseasesByNameOrCategory
        //name和category均为""情况
        Disease disease = new Disease();
        disease.setName("");
        disease.setCategory("");
        List<Disease> diseaseList = diseaseService.queryDiseases(disease);
        assertTrue(diseaseList.size() > 0);
    }

    @Test
    public void addDisease() {
        //无重复疾病
        Disease disease = new Disease();
        String name = "testAdd" + System.currentTimeMillis();
        disease.setName(name);
        disease.setCategory("传染病");
        assertTrue(diseaseService.addDisease(disease));
        assertFalse(diseaseService.addDisease(disease));
        assertTrue(diseaseService.deleteDisease(disease));
    }

    @Test
    public void updateDisease() {
        String name = "testAdd" + System.currentTimeMillis();
        Disease addDisease = new Disease();
        addDisease.setCategory("传染病");
        addDisease.setName(name);
        assertTrue(diseaseService.addDisease(addDisease));
        int id = addDisease.getId();
        Disease updateDisease = new Disease();
        updateDisease.setId(id);
        updateDisease.setCategory("内科");
        String updateName = "testUpdate" + System.currentTimeMillis();
        updateDisease.setName(updateName);

        assertTrue(diseaseService.updateDisease(updateDisease));
        assertTrue(diseaseService.deleteDisease(updateDisease));

        //没有该Disease时不能进行update
        assertFalse(diseaseService.updateDisease(updateDisease));

    }

    @Test
    public void deleteDisease() {
        String name = "testDelete" + System.currentTimeMillis();
        Disease addDisease = new Disease();
        addDisease.setCategory("传染病");
        addDisease.setName(name);
        assertTrue(diseaseService.addDisease(addDisease));
        assertTrue(diseaseService.deleteDisease(addDisease));

        //Disease不能重复删除
        assertFalse(diseaseService.deleteDisease(addDisease));

    }
}