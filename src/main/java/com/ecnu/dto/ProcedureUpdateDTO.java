package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcedureUpdateDTO implements Serializable{
    private int roleId;
    private String domain;
    private int newRoleId;
    private String newDomain;
}
