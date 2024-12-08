package com.hongyun.dto.vo;

import lombok.Data;


@Data
public class TargetItemVo {
    private Integer id;
    private String name;
    private String description;
    private int status;
    private String createTime;
}
