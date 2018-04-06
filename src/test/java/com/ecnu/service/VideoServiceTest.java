package com.ecnu.service;

import com.ecnu.entity.Video;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class VideoServiceTest extends BaseServiceTest {
    @Resource
    private VideoService videoService;

    @Test
    public void addProcedureVideo() throws Exception {
        Video video = new Video();
        video.setProcedureId(11);
        video.setUrl("testVideoUrl1");
        assertEquals(videoService.addProcedureVideo(video), 1);
    }

    @Test
    public void addProcedureVideos() throws Exception {
        List<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setProcedureId(11);
        video.setUrl("testVideoUrl2");
        videos.add(video);
        video = new Video();
        video.setProcedureId(11);
        video.setUrl("testVideoUrl3");
        videos.add(video);
        assertEquals(videoService.addProcedureVideos(videos), 2);
    }

    @Test
    public void deleteVideos() throws Exception {
        List<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setProcedureId(11);
        videos.add(video);
        int res = videoService.deleteVideos(videos); //批量删除
        assertTrue(res > 0);
    }

}