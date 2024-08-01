package com.hongyun.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Target {
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private Date createTime;
    private Integer day;
}
