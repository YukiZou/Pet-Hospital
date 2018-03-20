package com.ecnu.dto;

import com.ecnu.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * 将后端查询出的User的部分信息返回到前端界面
 */
@Data
public class ListUserDTO implements Serializable{
    private int id;
    private String userName;
    private int auth;
    //private String pictureUrl;

    public ListUserDTO(){

    }

    public ListUserDTO(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.auth = user.getAuth();
    }
}
