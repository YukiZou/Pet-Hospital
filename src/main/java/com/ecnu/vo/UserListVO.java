package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 将后端查询出的User的部分信息返回到前端界面
 */
@Data
public class UserListVO extends BaseResponse implements Serializable{
    private List<UserQueryVO> userList;

    public UserListVO(){

    }

    public UserListVO(String status) {
        super.setStatus(status);
    }

    public UserListVO(String status, List<UserQueryVO> userQueryVOList) {
        super.setStatus(status);
        this.userList = userQueryVOList;
    }
}
