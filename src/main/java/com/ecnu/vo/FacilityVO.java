package com.ecnu.vo;

import com.ecnu.entity.Facility;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询设备信息列表时将返回的 Facility 对象转换成 FacilityVO
 * 不封装status字段
 * @author asus
 */
@Data
public class FacilityVO implements Serializable{
    private int id;
    private String name;
    private String info;
    private String picture;

    public FacilityVO() {

    }

    public FacilityVO(Facility facility) {
        this.id = facility.getId();
        this.name = facility.getName();
        this.info = facility.getInfo();
        this.picture = facility.getPicture();
    }
}
