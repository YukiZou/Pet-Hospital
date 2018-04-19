package com.ecnu.vo;

import com.ecnu.entity.Procedure;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class ProcedureVO implements Serializable{
    private Integer roleId;
    private String domain;

    public ProcedureVO(){

    }
    public ProcedureVO(Procedure procedure) {
        this.roleId = procedure.getRoleId();
        this.domain = procedure.getDomain();
    }
}
