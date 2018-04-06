package com.ecnu.service.impl;

import com.ecnu.dao.ProcedureDao;
import com.ecnu.entity.Procedure;
import com.ecnu.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureServiceImpl implements ProcedureService {
    @Autowired
    private ProcedureDao procedureDao;
    /**
     * 新增流程（实际上是新增某个流程的某一步）
     * procedure 的 domain stepName info 字段注意要判断特殊字符的插入(controller层判断)
     * 限制： roleId不为空且有意义（1 2 3），domain不能重名且不为空（roleId + domain + step 可以唯一标识）,stepName 不为空
     * roleId前端传过来的不会无意义且为空，不用判断。
     * stepName如果前端给了不为空的字段，则直接写入数据库，如果给了空字段，那后端就根据step的int值给默认的stepName(如“第一步”)
     * controller层判断特殊字符和 domain 的不能重名，且返回对应错误
     * @param procedure
     * @return
     */
    @Override
    public int addProcedureStep(Procedure procedure) {
        return procedureDao.insertProcedureStep(procedure);
    }

    /**
     * 根据参数的指定字段查找符合条件的 Procedure list
     * 属性有值表示要匹配那个字段去查询,如果所有字段都为空，则是查询所有
     * 限制：暂时无
     * 如果没有找到匹配的记录，返回：一个size=0的list []
     * @param procedure
     * @return
     */
    @Override
    public List<Procedure> queryProcedureSteps(Procedure procedure) {
        return procedureDao.queryProcedureStep(procedure);
    }

    /**
     * 删除
     * procedure 所有条件都不匹配的话会清空整个表中的记录
     * @param procedures
     * @return
     */
    @Override
    public int deleteProcedureSteps(List<Procedure> procedures) {
        if (procedures == null || procedures.size() == 0) {
            return 0;
        }
        return procedureDao.deleteProcedureSteps(procedures);
    }

    @Override
    public int deleteProcedureSteps(Procedure procedure) {
        return procedureDao.deleteProcedureStep(procedure);
    }

    /**
     * 更新表记录
     * 不为空的字段值表示新值，id值不能为空
     * @param procedure
     * @return
     */
    @Override
    public int updateProcedureStep(Procedure procedure) {
        if (procedure.getId() <= 0) {
            return 0;
        }
        return procedureDao.updateProcedureStep(procedure);
    }

}
