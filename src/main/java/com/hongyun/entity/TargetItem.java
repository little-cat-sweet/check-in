package com.hongyun.entity;

import lombok.Data;

@Data
public class TargetItem {
    private Integer id;
    private Integer targetId;
    private Integer userId;
    private int status;
    private String createTime;
}
