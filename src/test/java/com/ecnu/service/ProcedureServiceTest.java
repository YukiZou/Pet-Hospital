package com.ecnu.service;

import com.ecnu.entity.Procedure;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class ProcedureServiceTest extends BaseServiceTest {
    @Resource
    private ProcedureService procedureService;

    /**
     * 新增流程（实际上是新增某个流程的某一步）
     * procedure 的 domain stepName info 字段注意要判断特殊字符的插入(controller层判断)
     * 限制： roleId不为空且有意义（1 2 3），domain不能重名且不为空（domain + step 可以唯一标识）,stepName 不为空
     * roleId前端传过来的不会无意义且为空，不用判断。
     * stepName如果前端给了不为空的字段，则直接写入数据库，如果给了空字段，那后端就根据step的int值给默认的stepName(如“第一步”)
     * controller层判断特殊字符和 domain 的不能重名，且返回对应错误
     * @throws Exception
     */
    @Test
    public void addProcedureStep() throws Exception {
        Procedure procedure = new Procedure();
        procedure.setRoleId(1);
        procedure.setDomain("前台流程1");
        procedure.setStep(1);
        procedure.setStepName("第一步");
        procedure.setInfo("前台用户的流程1的第一步的描述，具体要干什么事");
        assertEquals(procedureService.addProcedureStep(procedure), 1);
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

}