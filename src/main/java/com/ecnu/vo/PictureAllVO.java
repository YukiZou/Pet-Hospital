package com.ecnu.vo;

import com.ecnu.entity.Picture;
import lombok.Data;

import java.io.Serializable;

@Data
public class PictureAllVO implements Serializable {
    private int case_id;
    private int stage;

    public PictureAllVO() {

    }

    public PictureAllVO(Picture picture) {
        this.case_id=picture.getCaseId();
        this.stage=picture.getStage();
    }
}
