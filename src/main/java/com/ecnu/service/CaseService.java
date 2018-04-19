package com.ecnu.service;

import com.ecnu.entity.Case;

import java.util.List;

/**
 * @author asus
 */
public interface CaseService {
    /**
     * 新增疾病
     * @param c
     * @return
     */
    Boolean addCase(Case c);

    /**
     * 修改病例
     * @param c
     * @return
     */
    Boolean updateCase(Case c);

    /**
     * 删除病例（仅删除病例）
     * @param c
     * @return
     */
    Boolean deleteCase(Case c);
    /**
     * 根据参数case的数据来查找符合条件的case记录
     * @param c
     * @return
     */
    List<Case> queryCases(Case c);

    /**
     * 根据diseaseId找到所有的case
     * @param diseaseId
     * @return
     */
    List<Case> findCaseByDId(int diseaseId);
}
