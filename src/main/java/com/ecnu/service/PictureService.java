package com.ecnu.service;

import com.ecnu.entity.Picture;

import java.util.List;

/**
 * 病例/流程的图片
 */
public interface PictureService {
    /**
     * 新增与流程相关的图片
     * 新增流程的图片情况：参数 picture 的 procedureId 和 url 一定要有。
     * 调用dao层接口的前置条件：procedureId有意义且 url存在
     * 限制：确保 procedureId和 url 组的值唯一，避免重复图片的插入
     * @param picture
     * @return
     */
    int addProcedurePic(Picture picture);

    /**
     * 批量新增与流程相关的 picture 记录
     * 新增流程的图片情况：参数 picture 的 procedureId 和 url 一定要有。
     * 调用dao层接口的前置条件：procedureId有意义且 url存在
     * 限制：确保 procedureId和 url 组的值唯一，避免重复图片的插入
     * @param pictures
     * @return
     */
    int addProcedurePics(List<Picture> pictures);

    /**
     * 根据参数的指定字段查找符合条件的 picture list
     * 属性有值表示要匹配那个字段去查询,如果所有字段都为空，则是查询所有。
     * 限制：暂时无
     * 如果没有找到匹配的记录，返回：一个size=0的list []
     * @param picture
     * @return
     */
    List<Picture> queryPictures(Picture picture);
}
