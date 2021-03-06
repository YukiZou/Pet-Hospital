package com.ecnu.vo;

import com.ecnu.entity.Procedure;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProcedureVO implements Serializable{
    private int roleId;
    private String domain;

    public ProcedureVO(){

    }
    public ProcedureVO(Procedure procedure) {
        this.roleId = procedure.getRoleId();
        this.domain = procedure.getDomain();
    }
}
