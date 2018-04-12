package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoAllDTO implements Serializable {
    private int caseId;
    private int stage;
}
