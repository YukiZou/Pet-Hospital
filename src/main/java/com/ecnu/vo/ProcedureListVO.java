package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asus
 */
@Data
public class ProcedureListVO extends BaseResponse implements Serializable{
    private List<ProcedureVO> procedureList;

    public ProcedureListVO() {

    }

    public ProcedureListVO(String status) {
        super.setStatus(status);
    }

    public ProcedureListVO(String status, List<ProcedureVO> procedureList) {
        super.setStatus(status);
        this.procedureList = procedureList;
    }
}
