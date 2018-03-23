package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色扮演流程
 */
@Data
public class Procedure implements Serializable{
    private int id;
    private int role_id;//角色id，与RoleEnum对应
    private int step;//表示此节点属于流程的第几阶段
    private String procedureName;//流程名称
    private String info;//流程节点描述
}
