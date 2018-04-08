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
    public void addCaseVideo() throws Exception {
        Video video = new Video();
        video.setCaseId(1);
        video.setUrl("testVideoUrl1");
        assertTrue(videoService.addCaseVideo(video));
    }

    @Test
    public void deleteCaseVideo() throws Exception {
        Video video = new Video();
        video.setCaseId(1);
        video.setUrl("testUrl");
        assertTrue(videoService.addCaseVideo(video));
        assertTrue(videoService.deleteCaseVideo(video));

        //不能删除不存在的video
        assertFalse(videoService.deleteCaseVideo(video));

    }

    @Test
    public void findVideoById() throws Exception {
        //该id存在的情况
        Video v = videoService.findVideoById(1);
        assertTrue(v.getId() > 0);

        //该id不存在的情况
        Video vv = videoService.findVideoById(2);
        assertTrue(vv == null);
    }

    @Test
    public void addProcedureVideo() throws Exception {
        Video video = new Video();
        video.setProcedureId(12);
        video.setUrl("testVideoUrl1");
        assertEquals(videoService.addProcedureVideo(video), 1);
        List<Video> videos = new ArrayList<>();
        videos.add(video);
        assertEquals(videoService.deleteVideos(videos), 1);
    }

    @Test
    public void addProcedureVideos() throws Exception {
        List<Video> videos = new ArrayList<>();
        assertEquals(videoService.addProcedureVideos(videos), 0);
        Video video = new Video();
        video.setProcedureId(12);
        video.setUrl("testVideoUrl2");
        videos.add(video);
        video = new Video();
        video.setProcedureId(12);
        video.setUrl("testVideoUrl3");
        videos.add(video);
        assertEquals(videoService.addProcedureVideos(videos), 2);
        assertEquals(videoService.deleteVideos(videos), 2);
    }

    @Test
    public void queryVideos() throws Exception {
        //查询所有
        assertTrue(videoService.queryVideos(new Video()).size() > 0);
        //查询不存在的
        Video video = new Video();
        video.setId(1110);
        assertTrue(videoService.queryVideos(video).size() == 0);
    }

    @Test
    public void deleteVideos() throws Exception {
        List<Video> videos = new ArrayList<>();
        assertTrue(videoService.deleteVideos(videos) == 0);
        Video video = new Video();
        video.setProcedureId(12);
        video.setUrl("testDeleteVideoUrl");
        assertEquals(videoService.addProcedureVideo(video),1);
        videos.add(video);
        int res = videoService.deleteVideos(videos); //批量删除
        assertTrue(res > 0);

        assertEquals(videoService.deleteVideos(videos), 0);
    }

}