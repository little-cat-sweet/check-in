package com.hongyun.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TargetItem {
    private Integer id;
    private Integer targetId;
    private Integer userId;
    private int status;
    private Date createTime;
}
