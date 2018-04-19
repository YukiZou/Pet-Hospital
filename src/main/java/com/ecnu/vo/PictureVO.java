package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Picture;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class PictureVO  extends BaseResponse implements Serializable {
    private Integer id;
    public PictureVO() {

    }

    public PictureVO(String status) {
        super.setStatus(status);
    }

    public PictureVO(Picture picture) {
        this.id = picture.getId();
    }
}

