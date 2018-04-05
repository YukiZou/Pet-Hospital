package com.ecnu.dao;

import com.ecnu.entity.Video;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoDao {
    /**
     * 新增与流程相关的video 记录
     * 参数 video 的 procedureId 和 url 一定要有。
     * @param video
     * @return
     */
    int insertProcedureVideo(Video video);

    /**
     * 批量新增 新增与流程相关的video 记录们
     * 其他限制条件同 insertProcedureVideo 方法
     * list 不为null 且 list.size > 0
     * @param videos
     * @return
     */
    int insertProcedureVideos(List<Video> videos);

    /**
     * 新增与病例相关的video 记录
     * 参数 video 的 caseId stage 和 url 一定要有。
     * @param video
     * @return
     */
    //TODO: @黑猫实现对应的sql
    int insertCaseVideo(Video video);

    /**
     * 批量新增 新增与流程相关的video 记录们
     * 其他限制条件同 insertCaseVideo 方法
     * list 不为null 且 list.size > 0
     * @param videos
     * @return
     */
    //TODO: @黑猫实现
    int insertCaseVideos(List<Video> videos);

    /**
     * 根据参数的指定字段查找符合条件的 Video list
     * 属性有值表示要匹配那个字段去查询,如果所有字段都为空，则是查询所有
     * 限制：暂时无
     * 如果没有找到匹配的记录，返回：一个size=0的list []
     * @param video
     * @return
     */
    List<Video> queryVideos(Video video);
}
