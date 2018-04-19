package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class TextDTO implements Serializable {
    private int caseId;
    private int stage;
    private String info;
}
