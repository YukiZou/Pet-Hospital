package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asus
 */
@Data
public class PictureAllListVO extends BaseResponse implements Serializable {
    private List<PictureAllVO> pictureList;

    public PictureAllListVO() {

    }

    public PictureAllListVO(String status) {
        super.setStatus(status);
    }

    public PictureAllListVO(String status, List<PictureAllVO> pictureAllVOList) {
        super.setStatus(status);
        this.pictureList = pictureAllVOList;
    }
}
