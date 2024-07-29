package com.hongyun.service;

import com.hongyun.entity.TargetItem;

import java.util.Date;
import java.util.List;

public interface TargetItemService {

    boolean updateStatus(String status, Integer id);

    List<TargetItem> getTargetItems(Integer userId, Date time);

    boolean addItem(TargetItem targetItem);
}
