package com.hongyun.service;

import com.hongyun.entity.User;

public interface UserService {

    String register(User user);

    String login(String email, String password);

    String requestUpdatePasswordByEmail(String email);
}
