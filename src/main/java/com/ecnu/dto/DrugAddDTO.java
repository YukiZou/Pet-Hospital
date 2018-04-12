package com.ecnu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * add drug时前端传入的json字段解析成的对象
 */
@Data
public class DrugAddDTO implements Serializable {
    private String name;
    private String info;//药品描述
    private String picture;//前端将图片存入服务器上拿到的图片url传递给后端存到数据库表drug中
}
