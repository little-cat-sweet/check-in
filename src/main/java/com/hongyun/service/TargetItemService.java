package com.hongyun.service;

import com.hongyun.entity.TargetItem;

import java.util.List;

public interface TargetItemService {

    boolean updateStatus(String status, Integer id);

    List<TargetItem> getTargetItems(Integer userId, String time);

    boolean addItem(TargetItem targetItem);
}
