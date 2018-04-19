package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class DiseaseUpdateDTO implements Serializable {
    private int id;
    private String name;
    private String category;

}
