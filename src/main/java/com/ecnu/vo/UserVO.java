package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录方法返回的对象
 */
@Data
public class UserVO extends BaseResponse implements Serializable{
    private int id;
    private String userName;
    private int auth;
    private String pictureUrl;

    public UserVO() {

    }

    public UserVO(String status) {
        super.setStatus(status);
    }

    public UserVO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.auth = user.getAuth();
        this.pictureUrl = user.getPictureUrl();
    }
}
