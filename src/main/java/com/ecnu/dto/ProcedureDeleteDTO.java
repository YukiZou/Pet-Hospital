package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class ProcedureDeleteDTO implements Serializable{
    private int roleId;
    private String domain;
}
