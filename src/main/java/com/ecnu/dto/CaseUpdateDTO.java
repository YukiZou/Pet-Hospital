package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class CaseUpdateDTO  implements Serializable {
    private int id;
    private int diseaseId;
    private String name;

}