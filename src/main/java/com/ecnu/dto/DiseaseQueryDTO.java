package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class DiseaseQueryDTO implements Serializable {
    private String keyword;
}
