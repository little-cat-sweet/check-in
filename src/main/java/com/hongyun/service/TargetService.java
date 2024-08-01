package com.hongyun.service;

import com.hongyun.entity.Target;

import java.util.List;

public interface TargetService {

    int add(Target target);

    int update(String name, Integer id);

    List<Target> findByUserId(Integer userId);

    int delete(Integer id);
}
