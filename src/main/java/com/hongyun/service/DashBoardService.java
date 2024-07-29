package com.hongyun.service;

import com.hongyun.common.ResponseObjectVO;
import com.hongyun.entity.TargetItem;

import java.util.Date;

public interface DashBoardService {

    ResponseObjectVO<TargetItem> showItems(Date date, Integer userId);
}
