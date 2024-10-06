package com.hongyun.dto.vo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String token;


    public String toString(){
        return "user id -> " + this.id + " , user name -> " + this.name;
    }
}
