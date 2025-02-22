package com.hongyun.service.Impl;

import cn.hutool.log.Log;
import com.hongyun.common.PageVO;
import com.hongyun.entity.Target;
import com.hongyun.entity.TargetItem;
import com.hongyun.mapper.TargetMapper;
import com.hongyun.service.TargetItemService;
import com.hongyun.service.TargetService;
import com.hongyun.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TargetServiceImpl implements TargetService {

    private final Log log = Log.get();

    @Autowired
    private TargetMapper targetMapper;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private TargetItemService targetItemService;

    @Override
    public int add(Target target) {
        TargetItem targetItem = new TargetItem();
        targetMapper.addTarget(target);


        int id = target.getId();
        log.info("added target -> {}", target);
        targetItem.setTargetId(id);
        targetItem.setUserId(target.getUserId());
        targetItemService.addItem(targetItem);
        return 1;
    }


    @Override
    public int update(String name, Integer id) {
        return targetMapper.updateBy(name, id);
    }

    @Override
    public List<Target> findByUserId(Integer userId, PageVO pageVO) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        int offset = (pageVO.getPageNum() - 1) * pageVO.getPageSize();
        params.put("offset", offset);
        params.put("limit", pageVO.getPageSize());
        List<Target> targets = targetMapper.findByUserId(params);
        int total = targetMapper.countByUserId(params);
        pageVO.setTotal(total);
        return targets;
    }

    @Override
    public int delete(Integer id) {
        return targetMapper.deleteBy(id);
    }
}
