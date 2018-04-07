package com.ecnu.service;

import com.ecnu.entity.Case;

import java.util.List;

public interface CaseService {
    /**
     * 新增疾病
     * @param c
     */
    Boolean addCase(Case c);

    /**
     * 修改病例
     * @param c
     * @return
     */
    Boolean updateCase(Case c);

    /**
     * 根据参数case的数据来查找符合条件的case记录
     * @param c
     * @return
     */
    List<Case> queryCases(Case c);
    List<Case> findCaseByDId(int diseaseId);
}
