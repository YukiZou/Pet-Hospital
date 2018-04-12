package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yuanyuanzou on 2018/03/12
 * @Data 帮助自动生成getter/setter,不用程序员再去写大量的getter/setter代码
 * Serializable 才能序列化
 */
@Data
public class User implements Serializable{
    private int id;
    private String userName;
    private String pwd;
    private int auth;
    private String pictureUrl;

    public User() {

    }

    public User(int id, String userName, String pwd, String pictureUrl) {
        this.id = id;
        this.userName = userName;
        this.pwd = pwd;
        this.pictureUrl = pictureUrl;
    }

}
