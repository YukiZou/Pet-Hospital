package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Text;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class TextVO  extends BaseResponse implements Serializable {
    private Integer id;
    public TextVO() {

    }

    public TextVO(String status) {
        super.setStatus(status);
    }

    public TextVO(Text text) {
        this.id = text.getId();
    }
}
