package com.hongyun.entity;

import lombok.Data;

@Data
public class Target {
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private String createTime;
    private Integer day;
}
