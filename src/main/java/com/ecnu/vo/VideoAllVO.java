package com.ecnu.vo;

import com.ecnu.entity.Video;
import lombok.Data;

import java.io.Serializable;

@Data
public class VideoAllVO implements Serializable {
    private int case_id;
    private int stage;

    public VideoAllVO() {

    }

    public VideoAllVO(Video video) {
        this.case_id=video.getCaseId();
        this.stage=video.getStage();
    }
}
