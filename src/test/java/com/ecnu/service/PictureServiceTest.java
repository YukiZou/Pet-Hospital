package com.ecnu.service;

import com.ecnu.entity.Picture;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PictureServiceTest extends BaseServiceTest {
    @Resource
    private PictureService pictureService;

    /**
     * 新增流程的图片情况：参数 picture 的 procedureId 和 url 一定要有。
     * 新增病例的图片情况：参数 picture 的 caseId stage 和 url 一定要有。
     * 新增前判断参数是否为空
     * @throws Exception
     */
    @Test
    public void addProcedurePic() throws Exception {
        Picture picture = new Picture();
        picture.setProcedureId(11);
        picture.setUrl("testUrl");
        int res = pictureService.addProcedurePic(picture);
        assertEquals(res, 1);
    }

    @Test
    public  void addProcedurePics() throws Exception {
        List<Picture> pictures = new ArrayList<>();
        Picture picture = new Picture();
        picture.setProcedureId(11);
        picture.setUrl("testUrl2");
        pictures.add(picture);
        picture = new Picture();
        picture.setProcedureId(11);
        picture.setUrl("testUrl3");
        pictures.add(picture);
        System.out.println("size : " + pictures.size());
        int res = pictureService.addProcedurePics(pictures);
        assertEquals(res, 2);
    }

    @Test
    public void deletePictures() throws Exception {
        List<Picture> pictures = new ArrayList<>();
        Picture picture = new Picture();
        picture.setProcedureId(11);
        pictures.add(picture);
        pictureService.deletePictures(pictures); //批量删除
    }

}