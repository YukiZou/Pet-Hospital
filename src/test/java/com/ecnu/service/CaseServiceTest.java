package com.ecnu.service;

import org.junit.Test;
import com.ecnu.entity.Case;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class CaseServiceTest extends BaseServiceTest {

    @Resource
    private CaseService caseService;

    @Test
    public void addCase() {
        //无重复病例
        Case c = new Case();
        String name = "testAdd" + System.currentTimeMillis();
        c.setDiseaseId(1);
        c.setName(name);
        assertTrue(caseService.addCase(c));
        assertFalse(caseService.addCase(c));
        assertTrue(caseService.deleteCase(c));
    }

    @Test
    public void updateCase() {
        String name = "testUpdate" + System.currentTimeMillis();
        Case addCase = new Case();
        addCase.setDiseaseId(1);
        addCase.setName(name);
        assertTrue(caseService.addCase(addCase));
        int id = addCase.getId();
        Case updateCase = new Case();
        updateCase.setId(id);
        updateCase.setDiseaseId(1);
        String updateName = "testUpdate" + System.currentTimeMillis();
        updateCase.setName(updateName);

        assertTrue(caseService.updateCase(updateCase));
        assertTrue(caseService.deleteCase(addCase));

        //没有该Case时不能进行update
        assertFalse(caseService.updateCase(updateCase));
    }

    @Test
    public void deleteCase() {
        String name = "testDelete" + System.currentTimeMillis();
        Case addCase = new Case();
        addCase.setDiseaseId(1);
        addCase.setName(name);
        assertTrue(caseService.addCase(addCase));
        assertTrue(caseService.deleteCase(addCase));

        //Case不能重复删除
        assertFalse(caseService.deleteCase(addCase));

    }

    @Test
    public void queryCases() {
        //queryCasesByName
        //name为""情况
        Case c = new Case();
        c.setName("");
        List<Case> caseList = caseService.queryCases(c);
        assertTrue(caseList.size() > 0);
    }

    @Test
    public void findCaseByDId() {
        //该id存在的情况
        List<Case> queryCases = caseService.findCaseByDId(1);
        assertTrue(queryCases.size() > 0);

        //该id不存在的情况
        queryCases = caseService.findCaseByDId(31);
        assertTrue(queryCases.size() == 0);
    }
}