package com.ecnu.controller;

import com.ecnu.common.BaseResponse;
import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.dto.PictureDTO;
import com.ecnu.dto.PictureDeleteDTO;
import com.ecnu.entity.Picture;
import com.ecnu.service.PictureService;
import com.ecnu.vo.PictureVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/case/picture")
public class PictureController {

    private static Logger LOG = LoggerFactory.getLogger(PictureController.class);

    @Autowired
    private PictureService pictureService;

    /**
     * 新增picture
     * @param
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public PictureVO addPicture(@RequestBody PictureDTO pictureDTO) {
        try {
            //将pictureDTO对象转化成picture对象
            Picture picture=toPicture(pictureDTO);
            Boolean res=false;
            res=pictureService.addCasePic(picture);
            if(res){//新增picture成功
                PictureVO pictureVO=new PictureVO(picture);
                LOG.info("add picture : {} success", picture.toString());
                pictureVO.setStatus("success");
                return pictureVO;
            }
            else{
                LOG.error("add picture : {} failed", picture.toString());
                return new PictureVO("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("add picture failed");
            return new PictureVO("fail");
        }

    }

    /**
     * 删除picture
     * @param pictureDeleteDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deletePicture(@RequestBody PictureDeleteDTO pictureDeleteDTO) {
        try{
            Boolean res=false;
            int deletePictureId=pictureDeleteDTO.getId();
            Picture picture=pictureService.findPictureById(deletePictureId);
            res=pictureService.deleteCasePic(picture);
            if (res) {
                LOG.info("delete picture for picture id {} success!", pictureDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("delete picture for picture id {} failed!", pictureDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("delete picture failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }

    /**
     * 将PictureDTO对象转化成Picture对象
     * @param
     * @return
     */
    private Picture toPicture(PictureDTO pictureDTO) {
        //TODO:判断传回的数据是否为null或者""
        Picture picture=new Picture();
        picture.setCaseId(pictureDTO.getCaseId());
        picture.setStage(pictureDTO.getStage());
        picture.setUrl(pictureDTO.getUrl());
        return picture;
    }
}
