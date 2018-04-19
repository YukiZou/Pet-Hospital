package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Picture;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class ProcedurePicAddVO extends BaseResponse implements Serializable{
    private int id;
    private String url;

    public ProcedurePicAddVO() {

    }

    public ProcedurePicAddVO(String status) {
        super.setStatus(status);
    }

    public ProcedurePicAddVO(Picture picture) {
        this.id = picture.getId();
        this.url = picture.getUrl();
    }

    public ProcedurePicAddVO(String status, Picture picture) {
        super.setStatus(status);
        this.id = picture.getId();
        this.url = picture.getUrl();
    }
}
