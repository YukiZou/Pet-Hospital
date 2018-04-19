package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 根据名字模糊查找设备
 * @author asus
 */
@Data
public class FacilityQueryDTO implements Serializable {
    private String name;
}
