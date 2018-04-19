package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Disease;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asus
 */
@Data
public class DiseaseListVO  extends BaseResponse implements Serializable {
    private List<DiseaseQueryVO> diseaseList;

    public DiseaseListVO(){

    }

    public DiseaseListVO(String status) {
        super.setStatus(status);
    }

    public DiseaseListVO(String status, List<DiseaseQueryVO> diseaseQueryVOList) {
        super.setStatus(status);
        this.diseaseList = diseaseQueryVOList;
    }
}
