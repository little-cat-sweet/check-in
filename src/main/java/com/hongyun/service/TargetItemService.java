package com.hongyun.service;

import com.hongyun.entity.TargetItem;

import java.time.LocalDateTime;
import java.util.List;

public interface TargetItemService {

    boolean updateStatus(String status, Integer id);

    List<TargetItem> getTargetItems(Integer userId, LocalDateTime time);

    boolean addItem(TargetItem targetItem);
}
