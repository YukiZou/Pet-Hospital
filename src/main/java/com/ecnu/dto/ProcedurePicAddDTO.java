package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class ProcedurePicAddDTO implements Serializable{
    private int stepId;
    private String url;
}
