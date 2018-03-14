package com.ecnu.dto;

import com.ecnu.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable{
    private String userName;
    private String pwd;
    private int auth;
    private String pictureUrl;

    public UserDTO(){

    }

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.pwd = user.getPwd();
        this.auth = user.getAuth();
        this.pictureUrl = user.getPictureUrl();
    }
}
