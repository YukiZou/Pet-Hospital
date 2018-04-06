package com.ecnu.vo;

import com.ecnu.entity.Video;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProcedureVideoVO implements Serializable{
    private int id;
    private int procedureId;//流程管理的id
    private String url;

    public ProcedureVideoVO() {

    }

    public ProcedureVideoVO(Video video) {
        this.id = video.getId();
        this.procedureId = video.getProcedureId();
        this.url = video.getUrl();
    }
}
