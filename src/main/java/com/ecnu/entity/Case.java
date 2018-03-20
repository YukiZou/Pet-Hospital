package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 病例
 */
@Data
public class Case implements Serializable{
    private int id;//病例ID
    private int disease_id;
    private int name;
}
