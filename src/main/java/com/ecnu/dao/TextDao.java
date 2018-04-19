package com.ecnu.dao;

import com.ecnu.entity.Text;
import org.springframework.stereotype.Repository;

/**
 * @author asus
 */
@Repository
public interface TextDao {
    /**
     * 新增一个text
     * @param text
     * @return
     */
    int insertText(Text text);

    /**
     * 删除和病例相关的text
     * @param text
     * @return
     */
    int deleteText(Text text);

    /**
     * 根据id找 text记录
     * @param id
     * @return
     */
    Text findTextById(int id);

    /**
     * 修改text
     * @param text
     * @return
     */
    int updateText(Text text);

    /**
     * 通过caseId和stage找到text
     * @param text
     * @return
     */
    Text findTextByCIdS(Text text);
}
