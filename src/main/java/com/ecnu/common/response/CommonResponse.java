package com.ecnu.common.response;

import lombok.Data;

/**
 * Response基类
 */
@Data
public class CommonResponse {
    private int resCode;
    private String resMsg;
}
