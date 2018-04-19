package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Text;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class TextAllVO extends BaseResponse implements Serializable {
    private Integer id;
    private String info;
    public TextAllVO() {

    }

    public TextAllVO(String status) {
        super.setStatus(status);
    }

    public TextAllVO(Text text) {
        this.id = text.getId();
        this.info = text.getInfo();
    }
}
