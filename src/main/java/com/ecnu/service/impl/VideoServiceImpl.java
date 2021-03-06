package com.ecnu.service.impl;

import com.ecnu.dao.VideoDao;
import com.ecnu.entity.Video;
import com.ecnu.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    private static Logger LOG = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Autowired
    private VideoDao videoDao;

    /**
     * 新增与病例相关的视频
     */
    @Override
    public Boolean addCaseVideo(Video video){
        videoDao.insertCaseVideo(video);
        if (video.getId() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteCaseVideo(Video video) {
        int res = videoDao.deleteCaseVideo(video);
        if (res > 0) {
            LOG.info("删除视频成功");
            return true;
        }
        else {
            LOG.error("删除视频失败");
            return false;
        }
    }

    @Override
    public Video findVideoById(int id) {
        return videoDao.findVideoById(id);
    }

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
        //根据前端的逻辑判断，可能会有size==0的参数传过来，判断一下
        if (videos == null || videos.size() == 0) {
            return 0;
        }
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

    /**
     * 批量删除
     * video 所有条件都不匹配的话会清空整个表中的记录
     * @param videos
     * @return
     */
    @Override
    public int deleteVideos(List<Video> videos) {
        if (videos == null || videos.size() == 0) {
            return 0;
        }
        return videoDao.deleteVideos(videos);
    }
}
