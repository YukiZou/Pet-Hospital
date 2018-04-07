package com.ecnu.vo;

import com.ecnu.common.BaseResponse;

import java.io.Serializable;
import com.ecnu.entity.Case;
import lombok.Data;

@Data
public class CaseVO extends BaseResponse implements Serializable {
    private int id;
    public CaseVO() {

    }

    public CaseVO(String status) {
        super.setStatus(status);
    }

    public CaseVO(Case mcase) {
        this.id = mcase.getId();
    }
}
