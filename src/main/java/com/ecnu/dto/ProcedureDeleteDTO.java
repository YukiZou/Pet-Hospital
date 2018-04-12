package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcedureDeleteDTO implements Serializable{
    private int roleId;
    private String domain;
}
