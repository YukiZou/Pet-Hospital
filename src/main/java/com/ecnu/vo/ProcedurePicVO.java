package com.ecnu.vo;

import com.ecnu.entity.Picture;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class ProcedurePicVO implements Serializable{
    private Integer id;

    /**
     * 流程管理的id
     */
    private Integer procedureId;
    private String url;

    public ProcedurePicVO() {

    }

    public ProcedurePicVO(Picture picture) {
        this.id = picture.getId();
        this.procedureId = picture.getProcedureId();
        this.url = picture.getUrl();
    }
}
