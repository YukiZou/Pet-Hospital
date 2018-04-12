package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CaseUpdateDTO  implements Serializable {
    private int id;
    private int diseaseId;
    private String name;

}