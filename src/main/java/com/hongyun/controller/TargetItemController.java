package com.hongyun.controller;

import cn.hutool.log.Log;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import com.hongyun.entity.TargetItem;
import com.hongyun.service.TargetItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/targetItem")
public class TargetItemController {
    Log log = Log.get();

    @Autowired
    private TargetItemService targetItemService;

    @PostMapping(value = "/confirmSuccess")
    public ResponseObjectVO confirmSuccess(@RequestParam Integer id) {
        ResponseObjectVO responseObjectVO = new ResponseObjectVO();
        Boolean res = null;
        try {
            res = targetItemService.updateStatus(NormalConstants.TARGET_ITEM_SUCCESS, id);
            if (res) return responseObjectVO.getSuccessResponseVo(NormalConstants.SUCCESS);
            else return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
    }

    @PostMapping(value = "/cancel")
    public ResponseObjectVO cancel(@RequestParam Integer id) {
        ResponseObjectVO responseObjectVO = new ResponseObjectVO();
        Boolean res = null;
        try {
            res = targetItemService.updateStatus(NormalConstants.TARGET_ITEM_FAILED, id);
            if (res) return responseObjectVO.getSuccessResponseVo(NormalConstants.SUCCESS);
            else return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
    }


    @GetMapping(value = "/showItems")
    public ResponseObjectVO<List<TargetItem>> showTargetItems(@RequestParam Integer userId,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {

        ResponseObjectVO<List<TargetItem>> response = new ResponseObjectVO<>();
        List<TargetItem> data = null;
        try {
            data = targetItemService.getTargetItems(userId, time);
            return response.getSuccess(NormalConstants.SUCCESS, data);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return response.getFailResponseVo(NormalConstants.FAILED);
    }

    @PostMapping(value = "/addItem")
    public ResponseObjectVO addTargetItem(@RequestBody TargetItem targetItem) {
        ResponseObjectVO response = new ResponseObjectVO();
        Boolean success = null;
        try {
            success = targetItemService.addItem(targetItem);
            if (success) return response.getSuccessResponseVo(NormalConstants.SUCCESS);
            else response.getFailResponseVo(NormalConstants.FAILED);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return response.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
    }
}
