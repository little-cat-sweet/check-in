package com.hongyun.controller;

import cn.hutool.log.Log;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import com.hongyun.dto.vo.TargetItemVo;
import com.hongyun.dto.vo.User;
import com.hongyun.entity.TargetItem;
import com.hongyun.service.TargetItemService;
import com.hongyun.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseObjectVO<List<TargetItem>> showTargetItems(@RequestParam String time) {

        ResponseObjectVO<List<TargetItem>> response = new ResponseObjectVO<>();
        List<TargetItem> data = null;
        try {
            User user = UserHolder.getUser();
            data = targetItemService.getTargetItems(Math.toIntExact(user.getId()), time);
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

    @GetMapping(value = "/showTargetItemVo")
    public ResponseObjectVO<List<TargetItemVo>> showTargetItemVo(@RequestParam String time){
        ResponseObjectVO<List<TargetItemVo>> responseObjectVO = new ResponseObjectVO<>();
        List<TargetItemVo> data = new ArrayList<>();
        try {
            User user = UserHolder.getUser();
            data = targetItemService.getTargetItemVos(Math.toIntExact(user.getId()),time.substring(0, 10));
            return responseObjectVO.getSuccess(NormalConstants.SUCCESS, data);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
    }
}
