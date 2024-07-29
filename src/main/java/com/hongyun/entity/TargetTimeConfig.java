package com.hongyun.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TargetTimeConfig {

    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private Date createTime;
    private Character status;
    private Character day;

}
