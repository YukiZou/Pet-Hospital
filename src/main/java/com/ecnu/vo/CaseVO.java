package com.ecnu.vo;

import com.ecnu.common.BaseResponse;

import java.io.Serializable;
import com.ecnu.entity.Case;
import lombok.Data;

/**
 * @author asus
 */
@Data
public class CaseVO extends BaseResponse implements Serializable {
    private Integer id;
    public CaseVO() {

    }

    public CaseVO(String status) {
        super.setStatus(status);
    }

    public CaseVO(Case mcase) {
        this.id = mcase.getId();
    }
}
