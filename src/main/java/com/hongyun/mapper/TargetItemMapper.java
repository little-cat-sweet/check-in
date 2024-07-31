package com.hongyun.mapper;

import com.hongyun.entity.TargetItem;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TargetItemMapper {

    int updateStatus(String status, Integer id);

    List<TargetItem> findByUserIdAndCreateTime(Integer userId, LocalDateTime createTime);

    int addItem(TargetItem targetItem);
}
