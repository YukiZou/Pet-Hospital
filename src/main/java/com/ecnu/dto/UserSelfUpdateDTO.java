package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSelfUpdateDTO implements Serializable{
    private String userName;
    private String pwd;
    private String pictureUrl;
}
