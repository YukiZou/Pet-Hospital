package com.ecnu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色扮演流程
 * 数据库表中各字段均不为空
 * @author asus
 */
@Data
public class Procedure implements Serializable{
    private int id;

    /**
     * 角色id，与RoleEnum对应 1、2、3
     */
    private int roleId;

    /**
     * 流程名
     */
    private String domain;

    /**
     * 表示此节点属于流程的第几阶段
     */
    private int step;

    /**
     * 阶段名，类似于第一步，第二步这种（感觉没啥意义）
     */
    private String stepName;

    /**
     * 流程节点描述
     */
    private String info;
}
