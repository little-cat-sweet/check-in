package com.hongyun.service.Impl;

import cn.hutool.log.Log;
import com.hongyun.common.PageVO;
import com.hongyun.dto.vo.TargetItemVo;
import com.hongyun.entity.TargetItem;
import com.hongyun.mapper.TargetItemMapper;
import com.hongyun.service.TargetItemService;
import com.hongyun.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TargetItemServiceImpl implements TargetItemService {

    private final Log log = Log.get();

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
        log.info("userId -> {}, time -> {}", userId, time);
        return targetItemMapper.findByUserIdAndCreateTime(userId, time.substring(0, 10));
    }

    @Override
    public boolean addItem(TargetItem targetItem) {
        targetItem.setCreateTime(dateUtil.getYYYY_MM_DD_DateByNow());
        int res = targetItemMapper.addItem(targetItem);
        return res == 1;
    }

    @Override
    public List<TargetItemVo> getTargetItemVos(Integer userId, String time, PageVO page) {
        log.info("userId -> {}, time -> {}", userId, time);
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        int offset = (page.getPageNum() - 1) * page.getPageSize();
        params.put("offset", offset);
        params.put("limit", page.getPageSize());
        params.put("createTime", time.substring(0, 10));
        List<TargetItemVo> targetItemVos = targetItemMapper.showTargetItemVo(params);
        int total = targetItemMapper.getNowTimeTotalTargetItems(params);
        page.setTotal(total);
        return targetItemVos;
    }
}
