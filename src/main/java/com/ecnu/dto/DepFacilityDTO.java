package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asus
 */
@Data
public class DepFacilityDTO implements Serializable {
    private int id;
    private List<Integer> facilityList;
}
