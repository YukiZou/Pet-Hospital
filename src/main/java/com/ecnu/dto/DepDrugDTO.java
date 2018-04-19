package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asus
 */
@Data
public class DepDrugDTO implements Serializable{
    private int id;
    private List<Integer> drugList;
}
