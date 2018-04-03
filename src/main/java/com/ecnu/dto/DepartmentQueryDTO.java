package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentQueryDTO implements Serializable{
    private int id;
    private String name;
}
