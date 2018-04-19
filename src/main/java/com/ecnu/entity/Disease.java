package com.ecnu.entity;

import com.ecnu.dao.DiseaseDao;
import lombok.Data;

import java.io.Serializable;

/**
 * 病种
 * @author asus
 */
@Data
public class Disease implements Serializable{
    private int id;

    /**
     * 病种名，如蛔虫病。
     */
    private String name;

    /**
     * 大类别，如寄生虫病
     */
    private String category;
}
