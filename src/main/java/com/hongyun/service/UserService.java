package com.hongyun.service;

import com.hongyun.common.ResponseObjectVO;
import com.hongyun.entity.User;

import java.util.List;

public interface UserService {

    ResponseObjectVO<List<User>> findUsers();

    String register(User user);

    com.hongyun.dto.vo.User login(String email, String password);
}
