package com.hongyun.mapper;

import com.hongyun.entity.Target;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TargetMapper {

    int addTarget(Target target);

    List<Target> findByUserId(Integer userId);

    int updateBy(String name, Integer id);

    int deleteBy(Integer id);

    List<Target> findAll();
}
