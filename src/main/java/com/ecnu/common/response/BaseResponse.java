package com.ecnu.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Response基类
 */
@Data
public class BaseResponse implements Serializable{
//    private int code; //如200，400这种
//    private String msg; //如ok, accepted这种
//    private Object data;
    private String status;

    public BaseResponse(){

    }

    public BaseResponse(String status) {
        this.status = status;
    }

    /*public BaseResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg =msg;
        this.data = data;
    }*/
}
