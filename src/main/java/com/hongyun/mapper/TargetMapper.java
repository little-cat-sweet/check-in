package com.hongyun.mapper;

import com.hongyun.entity.Target;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface TargetMapper {

    int addTarget(Target target);

    List<Target> findByUserId(Map<String, Object> param);

    // 查询总记录数
    int countByUserId(Map<String, Object> params);

    int updateBy(String name, Integer id);

    int deleteBy(Integer id);

    List<Target> findAll();
    Target findById(int id);
}
