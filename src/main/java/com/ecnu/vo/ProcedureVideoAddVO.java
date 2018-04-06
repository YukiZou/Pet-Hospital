package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Video;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProcedureVideoAddVO extends BaseResponse implements Serializable{
    private int id;
    private String url;

    public ProcedureVideoAddVO() {

    }

    public ProcedureVideoAddVO(String status) {
        super.setStatus(status);
    }

    public ProcedureVideoAddVO(Video video) {
        this.id = video.getId();
        this.url = video.getUrl();
    }

    public ProcedureVideoAddVO(String status, Video video) {
        super.setStatus(status);
        this.id = video.getId();
        this.url = video.getUrl();
    }
}
