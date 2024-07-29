package com.hongyun.mapper;

import com.hongyun.entity.TargetItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface TargetItemMapper {

    int updateStatus(String status, Integer id);

    List<TargetItem> findByUserIdAndCreateTime(Integer userId, Date createTime);

    int addItem(TargetItem targetItem);
}
