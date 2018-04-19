package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class ProcedureStepDTO implements Serializable{
    private int id;
    private String stepName;
    private String info;
}
