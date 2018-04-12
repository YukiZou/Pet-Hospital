package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PictureDTO implements Serializable {
    private int caseId;
    private int stage;
    private String url;
}
