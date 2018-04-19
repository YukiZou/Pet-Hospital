package com.ecnu.vo;

import com.ecnu.entity.Picture;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class PictureAllVO implements Serializable {
    private Integer id;
    private String url;

    public PictureAllVO() {

    }

    public PictureAllVO(Picture picture) {
        this.id=picture.getId();
        this.url=picture.getUrl();
    }
}
