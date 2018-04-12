package com.ecnu.dto;

import com.ecnu.entity.Case;
import lombok.Data;

import java.io.Serializable;

/**
 * 增加病例时前端传回的数据
 */
@Data
public class CaseDTO implements Serializable {
    private int diseaseId;
    private String name;

}
