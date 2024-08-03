package com.hongyun.service.Impl;

import com.hongyun.entity.TargetItem;
import com.hongyun.mapper.TargetItemMapper;
import com.hongyun.service.TargetItemService;
import com.hongyun.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TargetItemServiceImpl implements TargetItemService {

    @Autowired
    private TargetItemMapper targetItemMapper;

    @Autowired
    private DateUtil dateUtil;

    @Override
    public boolean updateStatus(String status, Integer id) {
        int res = targetItemMapper.updateStatus(status, id);
        return res > 0;
    }

    @Override
    public List<TargetItem> getTargetItems(Integer userId, String time) {
        return targetItemMapper.findByUserIdAndCreateTime(userId, time);
    }

    @Override
    public boolean addItem(TargetItem targetItem) {
        targetItem.setCreateTime(dateUtil.getYYYY_MM_DD_DateByNow());
        int res = targetItemMapper.addItem(targetItem);
        return res == 1;
    }
}
