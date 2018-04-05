package com.ecnu.service.impl;

import com.ecnu.dao.VideoDao;
import com.ecnu.entity.Video;
import com.ecnu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoDao videoDao;
    /**
     * 新增与流程相关的视频
     * 新增流程的视频的情况：参数 video 的 procedureId 和 url 一定要有。
     * 调用dao层接口的前置条件：procedureId有意义且 url存在
     * 限制：确保 procedureId和 url 组的值唯一，避免重复图片的插入
     * @param video
     * @return
     */
    @Override
    public int addProcedureVideo(Video video) {
        //TODO:先检查输入的参数的正确性和意义再插入
        return videoDao.insertProcedureVideo(video);
    }

    /**
     * 批量新增与流程相关的 video 记录
     * 参数 video 的 procedureId 和 url 一定要有。
     * 调用dao层接口的前置条件：procedureId有意义且 url存在
     * 限制：确保 procedureId和 url 组的值唯一，避免重复图片的插入
     * @param videos
     * @return
     */
    @Override
    public int addProcedureVideos(List<Video> videos) {
        //TODO:先检查输入的参数的正确性和意义再插入
        return videoDao.insertProcedureVideos(videos);
    }

    /**
     * 根据参数的指定字段查找符合条件的 Video list
     * 属性有值表示要匹配那个字段去查询,如果所有字段都为空，则是查询所有
     * 限制：暂时无
     * 如果没有找到匹配的记录，返回：一个size=0的list []
     * @param video
     * @return
     */
    @Override
    public List<Video> queryVideos(Video video) {
        return videoDao.queryVideos(video);
    }
}
