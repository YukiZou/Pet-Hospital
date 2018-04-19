package com.ecnu.service;

import com.ecnu.entity.Text;

/**
 * @author asus
 */
public interface TextService {
    /**
     * 新增text
     * @param text
     * @return
     */
    Boolean addText(Text text);

    /**
     * 删除text
     * @param text
     * @return
     */
    Boolean deleteText(Text text);

    /**
     * 根据id 查找 text
     * @param id
     * @return
     */
    Text findTextById(int id);

    /**
     * 修改指定text
     * @param text
     * @return
     */
    Boolean updateText(Text text);

    /**
     * 根据参数查找 text
     * @param text
     * @return
     */
    Text findTextByCIdS(Text text);
}
