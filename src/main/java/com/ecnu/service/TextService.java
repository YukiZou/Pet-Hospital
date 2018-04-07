package com.ecnu.service;

import com.ecnu.entity.Text;

public interface TextService {
    /**
     * 新增text
     * @param text
     */
    Boolean addText(Text text);

    /**
     * 删除text
     */
    Boolean deleteText(Text text);

    Text findTextById(int id);

    /**
     * 修改指定text
     * @param text
     * @return
     */
    Boolean updateText(Text text);

    Text findTextByCIdS(Text text);
}
