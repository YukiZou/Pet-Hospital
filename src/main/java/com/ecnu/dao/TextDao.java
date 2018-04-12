package com.ecnu.dao;

import com.ecnu.entity.Text;
import org.springframework.stereotype.Repository;

@Repository
public interface TextDao {
    /**
     * 新增一个text
     * @param text
     */
    int insertText(Text text);

    /**
     * 删除和病例相关的text
     */
    int deleteText(Text text);

    Text findTextById(int id);

    /**
     * 修改text
     * @param text
     * @return
     */
    int updateText(Text text);

    /**
     * 通过caseId和stage找到text
     */
    Text findTextByCIdS(Text text);
}
