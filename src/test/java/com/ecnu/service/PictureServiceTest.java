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
        picture.setProcedureId(12);
        picture.setUrl("testUrl");
        int res = pictureService.addProcedurePic(picture);
        assertEquals(res, 1);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(picture);
        assertEquals(pictureService.deletePictures(pictures), 1);
    }

    @Test
    public void addCasePic() throws Exception {
        Picture picture = new Picture();
        picture.setCaseId(1);
        picture.setUrl("testUrl");
        assertTrue(pictureService.addCasePic(picture));
    }

    @Test
    public  void addProcedurePics() throws Exception {
        List<Picture> pictures = new ArrayList<>();
        assertEquals(pictureService.addProcedurePics(pictures), 0);
        Picture picture = new Picture();
        picture.setProcedureId(12);
        picture.setUrl("testUrl2");
        pictures.add(picture);
        picture = new Picture();
        picture.setProcedureId(12);
        picture.setUrl("testUrl3");
        pictures.add(picture);
        int res = pictureService.addProcedurePics(pictures);
        assertEquals(res, 2);
        assertEquals(pictureService.deletePictures(pictures), 2);
    }

    @Test
    public void queryPictures() throws Exception {
        //查询所有
        assertTrue(pictureService.queryPictures(new Picture()).size() > 0);
        //查询不存在的
        Picture picture = new Picture();
        picture.setId(1110);
        assertTrue(pictureService.queryPictures(picture).size() == 0);
    }

    @Test
    public void deleteCasePic() throws Exception {
        Picture picture = new Picture();
        picture.setCaseId(1);
        picture.setUrl("testUrl");
        assertTrue(pictureService.addCasePic(picture));
        assertTrue(pictureService.deleteCasePic(picture));

        //不能删除不存在的picture
        assertFalse(pictureService.deleteCasePic(picture));
    }

    @Test
    public void deletePictures() throws Exception {
        List<Picture> pictures = new ArrayList<>();
        assertTrue(pictureService.deletePictures(pictures) == 0);
        Picture picture = new Picture();
        picture.setProcedureId(12);
        picture.setUrl("testDeleteUrl");
        assertEquals(pictureService.addProcedurePic(picture), 1);

        pictures.add(picture);
        assertEquals(pictureService.deletePictures(pictures), 1);
        //再删一次
        assertEquals(pictureService.deletePictures(pictures), 0);
    }

    @Test
    public void findPictureById() throws Exception {
        //该id存在的情况
        Picture p = pictureService.findPictureById(31);
        assertTrue(p.getId() > 0);

        //该id不存在的情况
        Picture pp = pictureService.findPictureById(2);
        assertTrue(pp == null);
    }

}