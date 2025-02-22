package com.hongyun.service;

import com.hongyun.common.PageVO;
import com.hongyun.entity.Target;

import java.util.List;

public interface TargetService {

    int add(Target target);

    int update(String name, Integer id);

    List<Target> findByUserId(Integer userId, PageVO pageVO);

    int delete(Integer id);
}
