package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcedureVideoAddDTO implements Serializable{
    private int stepId; // 即 procedureId
    private String url;
}
