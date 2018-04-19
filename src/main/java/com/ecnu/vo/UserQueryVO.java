package com.ecnu.vo;

import com.ecnu.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询用户信息列表时返回的数据。
 * @author asus
 */
@Data
public class UserQueryVO implements Serializable {
    private Integer id;
    private String userName;
    private Integer auth;

    public UserQueryVO() {

    }

    public UserQueryVO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.auth = user.getAuth();
    }
}
