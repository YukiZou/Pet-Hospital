package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Facility;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备增加方法返回给前端的数据,封装了status
 * @author asus
 */
@Data
public class FacilityAddVO extends BaseResponse implements Serializable{
    private Integer id;
    private String name;
    private String info;

    public FacilityAddVO() {

    }

    public FacilityAddVO(String status) {
        super.setStatus(status);
    }

    public FacilityAddVO(Facility facility) {
        this.id = facility.getId();
        this.name = facility.getName();
        this.info = facility.getInfo();
    }
}
