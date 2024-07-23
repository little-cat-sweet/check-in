package com.hongyun.util;

import com.hongyun.dto.vo.User;

public class UserHolder {

    private static final ThreadLocal<User> tl = new ThreadLocal<>();

    public static void setUser(User user){
        tl.set(user);
    }

    public static User getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
