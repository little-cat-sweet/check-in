package com.hongyun.service;

import com.hongyun.dto.vo.TargetItemVo;
import com.hongyun.entity.TargetItem;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface TargetItemService {

    boolean updateStatus(String status, Integer id);

    List<TargetItem> getTargetItems(Integer userId, String time);

    boolean addItem(TargetItem targetItem);

    List<TargetItemVo> getTargetItemVos(Integer userId, String time);
}
