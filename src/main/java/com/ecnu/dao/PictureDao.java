package com.ecnu.dao;

import com.ecnu.entity.Picture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureDao {

    /**
     * 新增与流程相关的图片
     * 新增流程的图片情况：参数 picture 的 procedureId 和 url 一定要有。
     * int 型没有值的字段会默认插入0到数据库中，读取的时候注意判断
     * @param picture
     * @return
     */
    int insertProcedurePic(Picture picture);

    /**
     * 批量新增与流程相关的 picture 记录
     * 其他限制条件同 insertProcedurePic 方法
     * list 不为null 且 list.size > 0
     * @param pictures
     * @return
     */
    int insertProcedurePics(List<Picture> pictures);

    /**
     * 新增与病例相关的图片
     * 新增病例的图片情况：参数 picture 的 caseId stage 和 url 一定要有。
     * @param picture
     * @return
     */
    //TODO: @黑猫实现
    int insertCasePic(Picture picture);

    /**
     * 批量新增与病例相关的 picture 记录
     * 其他限制条件同 insertCasePic 方法
     * list 不为null 且 list.size > 0
     * @param pictures
     * @return
     */
    //TODO: @黑猫实现
    int insertCasePics(List<Picture> pictures);

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
