package com.hongyun.service.Impl;

import com.hongyun.entity.Target;
import com.hongyun.mapper.TargetMapper;
import com.hongyun.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TargetServiceImpl implements TargetService {

    @Autowired
    private TargetMapper targetMapper;

    @Override
    public int add(Target target) {
        return targetMapper.addTarget(target);
    }

    @Override
    public int update(String name, Integer id) {
        return targetMapper.updateBy(name, id);
    }

    @Override
    public List<Target> findByUserId(Integer userId) {
        return targetMapper.findByUserId(userId);
    }

    @Override
    public int delete(Integer id) {
        return targetMapper.deleteBy(id);
    }
}
