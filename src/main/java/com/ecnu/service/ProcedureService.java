package com.ecnu.service;

import com.ecnu.entity.Procedure;

import java.util.List;

/**
 * 流程管理
 */
public interface ProcedureService {
    /**
     * 新增流程（实际上是新增某个流程的某一步）
     * procedure 的 domain stepName info 字段注意要判断特殊字符的插入(controller层判断)
     * 不为空的字段： roleId不为空且有意义（1 2 3），domain不能重名（domain + step 可以唯一标识） stepName info
     * @param procedure
     * @return
     */
    int addProcedureStep(Procedure procedure);

    /**
     * 根据参数的指定字段查找符合条件的 Procedure list
     * 属性有值表示要匹配那个字段去查询,如果所有字段都为空，则是查询所有
     * 限制：暂时无
     * 如果没有找到匹配的记录，返回：一个size=0的list []
     * @param procedure
     * @return
     */
    List<Procedure> queryProcedureSteps(Procedure procedure);
}
