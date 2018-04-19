package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 将后端查询出来的 FacilityVO list对象封装上status返回到前端界面
 * @author asus
 */
@Data
public class FacilityListVO extends BaseResponse implements Serializable{
    private List<FacilityVO> facilityList;

    public FacilityListVO() {

    }

    public FacilityListVO(String status) {
        super.setStatus(status);
    }

    public FacilityListVO(String status, List<FacilityVO> facilityList) {
        super.setStatus(status);
        this.facilityList = facilityList;
    }
}
