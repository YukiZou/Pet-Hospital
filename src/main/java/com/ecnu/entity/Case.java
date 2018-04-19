package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 病例
 * @author asus
 */
@Data
public class Case implements Serializable{
    /**
     * 病例ID
     */
    private int id;
    private int diseaseId;
    private String name;
}
