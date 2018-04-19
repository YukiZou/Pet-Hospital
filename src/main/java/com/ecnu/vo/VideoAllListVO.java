package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asus
 */
@Data
public class VideoAllListVO extends BaseResponse implements Serializable {
    private List<VideoAllVO> videoList;

    public VideoAllListVO() {

    }

    public VideoAllListVO(String status) {
        super.setStatus(status);
    }

    public VideoAllListVO(String status, List<VideoAllVO> videoAllVOList) {
        super.setStatus(status);
        this.videoList = videoAllVOList;
    }
}
