package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asus
 */
@Data
public class ProcedureStepListVO extends BaseResponse implements Serializable{
    private List<ProcedureStepVO> stepList;

    public ProcedureStepListVO() {

    }

    public ProcedureStepListVO(String status) {
        super.setStatus(status);
    }

    public ProcedureStepListVO(String status, List<ProcedureStepVO> stepList) {
        super.setStatus(status);
        this.stepList = stepList;
    }
}
