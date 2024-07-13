package com.hongyun.mapper;

import com.hongyun.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getAll();

    int insert(User user);
}
