package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TextUpdateDTO implements Serializable {
    private int id;
    private String info;
}
