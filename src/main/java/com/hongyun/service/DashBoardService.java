package com.hongyun.service;

import com.hongyun.common.ResponseObjectVO;
import com.hongyun.entity.DashTargetItem;
import com.hongyun.entity.TargetItem;

import java.util.Date;
import java.util.List;

public interface DashBoardService {

    ResponseObjectVO<TargetItem> showItems(Date date, Integer userId);

    ResponseObjectVO<List<DashTargetItem>> getDashTargetItems(Integer userId);
}
