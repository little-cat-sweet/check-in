package com.hongyun.mapper;

import com.hongyun.dto.vo.TargetItemVo;
import com.hongyun.entity.DashTargetItem;
import com.hongyun.entity.TargetItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TargetItemMapper {

    int updateStatus(String status, Integer id);

    List<TargetItem> findByUserIdAndCreateTime(Integer userId, String createTime);

    int addItem(TargetItem targetItem);

    int addItems(List<TargetItem> targetItems);

    List<TargetItemVo> showTargetItemVo(Map<String, Object> param);

    int getNowTimeTotalTargetItems(Map<String, Object> params);

    List<DashTargetItem> getDashTargetItem(Map<String, Object> params);

    int deleteByTargetId(Integer targetId);
}
