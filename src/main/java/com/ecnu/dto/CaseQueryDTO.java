package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CaseQueryDTO implements Serializable {
    private String keyword;
}