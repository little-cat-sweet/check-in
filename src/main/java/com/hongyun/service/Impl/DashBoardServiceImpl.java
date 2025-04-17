package com.hongyun.service.Impl;

import com.hongyun.common.Constant;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.entity.DashTargetItem;
import com.hongyun.entity.TargetItem;
import com.hongyun.mapper.TargetItemMapper;
import com.hongyun.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private TargetItemMapper targetItemMapper;

    @Override
    public ResponseObjectVO<TargetItem> showItems(Date date, Integer userId) {
        return null;
    }

    @Override
    public ResponseObjectVO<List<DashTargetItem>> getDashTargetItems(Integer userId) {
        LocalDate currentDate = LocalDate.now();
        LocalDate pastDate = currentDate.minusDays(365);

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("fromDate", pastDate.toString());
        params.put("endDate", currentDate.toString());
        List<DashTargetItem> data = targetItemMapper.getDashTargetItem(params);

        ResponseObjectVO<List<DashTargetItem>> res = new ResponseObjectVO<>();
        res = res.getSuccessResponseVo(Constant.SUCCESS);
        res.setData(data);
        return res;
    }
}
