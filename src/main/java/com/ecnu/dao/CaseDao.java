package com.ecnu.dao;

import com.ecnu.entity.Case;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseDao {


    List<Case> findCasesByDId(int diseaseId);
    /**
     * 根据参数case查询出所有符合查询条件的case
     * @param c
     * @return
     */
    List<Case> queryCases(Case c);
    /**
     * 新增一个病例
     * @param c
     */
    void insertCase(Case c);
    Case findCaseByName(String name);

    /**
     * 根据参数case的ID找到要更改的病例记录，然后update该条记录
     * @param c
     */
    int updateCase(Case c);

    /**
     * 删除病例（仅删除病例表）
     * @param c
     * @return
     */
    int deleteCase(Case c);
}
