package com.ecnu.controller;

import com.ecnu.common.BaseResponse;
import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.dto.VideoDeleteDTO;
import com.ecnu.dto.VideoDTO;
import com.ecnu.entity.Video;
import com.ecnu.service.VideoService;
import com.ecnu.vo.VideoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/case/video")
public class VideoController {

    private static Logger LOG = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    /**
     * 新增video
     * @param
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public VideoVO addVideo(@RequestBody VideoDTO videoDTO) {
        try {
            //将videoDTO对象转化成video对象
            Video video=toVideo(videoDTO);
            Boolean res=false;
            res=videoService.addCaseVideo(video);
            if(res){//新增video成功
                VideoVO videoVO=new VideoVO(video);
                LOG.info("add video : {} success", video.toString());
                videoVO.setStatus("success");
                return videoVO;
            }
            else{
                LOG.error("add video : {} failed", video.toString());
                return new VideoVO("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("add video failed");
            return new VideoVO("fail");
        }

    }

    /**
     * 删除video
     * @param videoDeleteDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteVideo(@RequestBody VideoDeleteDTO videoDeleteDTO) {
        try{
            Boolean res=false;
            int deleteVideoId=videoDeleteDTO.getId();
            Video video=videoService.findVideoById(deleteVideoId);
            res=videoService.deleteCaseVideo(video);
            if (res) {
                LOG.info("delete video for video id {} success!", videoDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("delete video for video id {} failed!", videoDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("delete video failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }
    /**
     * 将VideoDTO对象转化成PVideo对象
     * @param
     * @return
     */
    private Video toVideo(VideoDTO videoDTO) {
        //TODO:判断传回的数据是否为null或者""
        Video video=new Video();
        video.setCaseId(videoDTO.getCaseId());
        video.setStage(videoDTO.getStage());
        video.setUrl(videoDTO.getUrl());
        return video;
    }
}
