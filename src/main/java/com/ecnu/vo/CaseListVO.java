package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CaseListVO  extends BaseResponse implements Serializable {
    private List<CaseQueryVO> caseList;

    public CaseListVO(){

    }

    public CaseListVO(String status) {
        super.setStatus(status);
    }

    public CaseListVO(String status, List<CaseQueryVO> caseQueryVOList) {
        super.setStatus(status);
        this.caseList = caseQueryVOList;
    }
}
