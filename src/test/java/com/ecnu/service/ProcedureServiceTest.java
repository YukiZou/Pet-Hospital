package com.ecnu.service;

import com.ecnu.entity.Procedure;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

public class ProcedureServiceTest extends BaseServiceTest {
    @Resource
    private ProcedureService procedureService;

    @Test
    public void addAndDeleteProcedureStep() throws Exception {
        Procedure procedure = new Procedure();
        procedure.setRoleId(1);
        procedure.setDomain("前台流程1");
        procedure.setStep(1);
        procedure.setStepName("第一步");
        procedure.setInfo("前台用户的流程1的第一步的描述，具体要干什么事");
        assertEquals(procedureService.addProcedureStep(procedure), 1);
        //为了不影响数据库，再把刚加入的记录删掉
        assertEquals(procedureService.deleteProcedureSteps(procedure), 1);
        //再删一次
        assertEquals(procedureService.deleteProcedureSteps(procedure), 0);
    }

    @Test
    public void queryProcedureSteps() throws Exception {
        //找不到的情况，看一下返回值
        Procedure procedure = new Procedure();
        procedure.setRoleId(2);
        procedure.setStep(1);
        assertEquals(procedureService.queryProcedureSteps(procedure).size(), 0);
        procedure.setRoleId(1);
        procedure.setStep(1);
        assertEquals(procedureService.queryProcedureSteps(procedure).size(), 1);
    }

    @Test
    public void updateProcedureStep() throws Exception {
        Procedure procedure = new Procedure();
        assertEquals(procedureService.updateProcedureStep(procedure), 0);
        procedure.setId(25);
        List<Procedure> procedureList = procedureService.queryProcedureSteps(procedure);
        assertEquals(procedureList.size(), 1);
        Procedure queryProcedure = procedureList.get(0);
        String oldInfo = queryProcedure.getInfo();
        String newInfo = "test update info";
        Procedure updateProcedure = new Procedure();
        updateProcedure.setId(queryProcedure.getId());
        updateProcedure.setInfo(newInfo);
        assertEquals(procedureService.updateProcedureStep(updateProcedure), 1);
        //恢复之前的更改，保证测试方法不影响数据库中的数据
        updateProcedure.setInfo(oldInfo);
        assertEquals(procedureService.updateProcedureStep(updateProcedure), 1);
    }

}