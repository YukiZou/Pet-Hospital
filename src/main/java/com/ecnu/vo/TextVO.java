package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Text;
import lombok.Data;

import java.io.Serializable;

@Data
public class TextVO  extends BaseResponse implements Serializable {
    private int id;
    public TextVO() {

    }

    public TextVO(String status) {
        super.setStatus(status);
    }

    public TextVO(Text text) {
        this.id = text.getId();
    }
}
