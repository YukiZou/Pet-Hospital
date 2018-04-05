package com.ecnu.dao;

import com.ecnu.entity.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedureDao {

    /**
     * 新增一条procedure表记录
     * 一般情况下：roleId domain step stepName info都要有（如果有图片和视频去新增picture video 记录）
     * 其中， roleId domain step stepName info必须要有(数据库的限制)
     * @param procedure
     * @return
     */
    int insertProcedureStep(Procedure procedure);

    /**
     * 根据参数的指定字段查找符合条件的 Procedure list
     * 属性有值表示要匹配那个字段去查询,如果所有字段都为空，则是查询所有
     * 限制：暂时无
     * @param procedure
     * @return
     */
    List<Procedure> queryProcedureStep(Procedure procedure);
}
