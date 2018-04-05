package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 将后端查询出来的Drug list对象封装上status返回到前端界面
 */
@Data
public class DrugListVO extends BaseResponse implements Serializable{
    private List<DrugVO> drugList;

    public DrugListVO() {

    }

    public DrugListVO(String status) {
        super.setStatus(status);
    }

    public DrugListVO(String status, List<DrugVO> drugList) {
        super.setStatus(status);
        this.drugList = drugList;
    }
}
