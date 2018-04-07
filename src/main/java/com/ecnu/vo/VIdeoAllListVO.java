package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VIdeoAllListVO extends BaseResponse implements Serializable {
    private List<VideoAllVO> videoAllVOList;

    public VIdeoAllListVO() {

    }

    public VIdeoAllListVO(String status) {
        super.setStatus(status);
    }

    public VIdeoAllListVO(String status, List<VideoAllVO> videoAllVOList) {
        super.setStatus(status);
        this.videoAllVOList = videoAllVOList;
    }
}
