package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author asus
 */
@Data
public class UserDTO implements Serializable{
    private String userName;
    private String pwd;
    private int auth;
    private String pictureUrl;
}
