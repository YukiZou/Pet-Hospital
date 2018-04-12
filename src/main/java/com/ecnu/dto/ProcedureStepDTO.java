package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcedureStepDTO implements Serializable{
    private int id;
    private String stepName;
    private String info;
}
