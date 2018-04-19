package com.ecnu.vo;

import com.ecnu.entity.Video;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class VideoAllVO implements Serializable {
    private Integer id;
    private String url;

    public VideoAllVO() {

    }

    public VideoAllVO(Video video) {
        this.id=video.getId();
        this.url=video.getUrl();
    }
}
