package com.hongyun.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String headImage;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}