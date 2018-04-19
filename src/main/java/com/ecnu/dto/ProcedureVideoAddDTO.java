package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class ProcedureVideoAddDTO implements Serializable{
    /**
     * 即 procedureId
     */
    private int stepId;
    private String url;
}
