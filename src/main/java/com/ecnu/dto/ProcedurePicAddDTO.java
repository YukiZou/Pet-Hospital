package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcedurePicAddDTO implements Serializable{
    private int stepId;
    private String url;
}
