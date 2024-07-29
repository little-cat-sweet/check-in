package com.hongyun.service.Impl;

import com.hongyun.common.ResponseObjectVO;
import com.hongyun.entity.TargetItem;
import com.hongyun.service.DashBoardService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Override
    public ResponseObjectVO<TargetItem> showItems(Date date, Integer userId) {
        return null;
    }
}
