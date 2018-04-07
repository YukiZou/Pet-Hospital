package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Video;
import lombok.Data;

import java.io.Serializable;

@Data
public class VideoVO extends BaseResponse implements Serializable {
    private int id;
    public VideoVO() {

    }

    public VideoVO(String status) {
        super.setStatus(status);
    }

    public VideoVO(Video video) {
        this.id = video.getId();
    }
}
