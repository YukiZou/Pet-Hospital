package com.ecnu.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Response基类
 * @author zou yuanyuan
 */
@Data
public class BaseResponse implements Serializable{
    /**
     * status值：success, sqlFail, authFail,inputFail, fail
     */
    private String status;

    public BaseResponse(){

    }

    public BaseResponse(String status) {
        this.status = status;
    }
}
