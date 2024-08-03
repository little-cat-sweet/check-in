package com.hongyun.schedule;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.log.Log;
import com.hongyun.constants.NormalConstants;
import com.hongyun.entity.Target;
import com.hongyun.entity.TargetItem;
import com.hongyun.mapper.TargetItemMapper;
import com.hongyun.mapper.TargetMapper;
import com.hongyun.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenerateTargetItems {

    private final Log log = Log.get();

    @Autowired
    private TargetMapper targetMapper;

    @Autowired
    private TargetItemMapper targetItemMapper;

    @Autowired
    private DateUtil dateUtil;

    @Scheduled(cron = "0 0 0 * * *") // 每天凌晨零点触发
//    @Scheduled(fixedDelay = 10000)
    public void generateTargetItems() {

        int nowWeekDay = getWeekDay();

        List<Target> targets = targetMapper.findAll();
        List<TargetItem> targetItems = targets.stream()
                .filter(target -> target.getDay() == nowWeekDay || target.getDay() == 0)
                .map(target -> {
                    TargetItem targetItem = new TargetItem();
                    targetItem.setTargetId(target.getId());
                    targetItem.setCreateTime(dateUtil.getYYYY_MM_DD_DateByNow());
                    targetItem.setUserId(target.getUserId());
                    return targetItem;
                })
                .collect(Collectors.toList());

        Integer res = null;
        if (CollectionUtil.isNotEmpty(targetItems)) {
            try {
                res = targetItemMapper.addItems(targetItems);
                if (res > 0) {
                    log.info(NormalConstants.SUCCESS);
                } else {
                    log.info(NormalConstants.FAILED);
                }
            } catch (Exception e) {
                log.error(NormalConstants.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            log.warn("no target item need to add, pass process. Time -> {}", LocalDateTime.now());
        }

    }

    private int getWeekDay() {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        return dayOfWeek.getValue();
    }

}
