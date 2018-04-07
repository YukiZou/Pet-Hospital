package com.ecnu.service;

import com.ecnu.entity.Video;

import java.util.List;

/**
 * 病例/流程的视频
 */
public interface VideoService {
    /**
     * 新增与病例相关的视频
     */
    Boolean addCaseVideo(Video video);

    /**
     * 删除与病例相关的视频
     */
    Boolean deleteCaseVideo(Video video);


    /**
     * 新增与流程相关的视频
     * 新增流程的视频的情况：参数 video 的 procedureId 和 url 一定要有。
     * 调用dao层接口的前置条件：procedureId有意义且 url存在
     * 限制：确保 procedureId和 url 组的值唯一，避免重复图片的插入
     * @param video
     * @return
     */
    int addProcedureVideo(Video video);

    /**
     * 批量新增与流程相关的 video 记录
     * 参数 video 的 procedureId 和 url 一定要有。
     * 调用dao层接口的前置条件：procedureId有意义且 url存在
     * 限制：确保 procedureId和 url 组的值唯一，避免重复图片的插入
     * @param videos
     * @return
     */
    int addProcedureVideos(List<Video> videos);

    /**
     * 根据参数的指定字段查找符合条件的 Video list
     * 属性有值表示要匹配那个字段去查询,如果所有字段都为空，则是查询所有
     * 限制：暂时无
     * 如果没有找到匹配的记录，返回：一个size=0的list []
     * @param video
     * @return
     */
    List<Video> queryVideos(Video video);

    Video findVideoById(int id);

    List<Video> queryVideosByStepIds(List<Integer> procedureIds);

    /**
     * 批量删除
     * video 所有条件都不匹配的话会清空整个表中的记录
     * @param videos
     * @return
     */
    int deleteVideos(List<Video> videos);
}
