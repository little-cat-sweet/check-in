package com.hongyun.controller;

import cn.hutool.log.Log;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import com.hongyun.entity.Target;
import com.hongyun.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/target")
public class TargetController {

    private final Log log = Log.get();

    @Autowired
    private TargetService targetService;

    @PostMapping(value = "/add")
    public ResponseObjectVO<String> addTarget(@RequestBody Target target) {
        Integer res = null;
        ResponseObjectVO<String> responseObjectVO = new ResponseObjectVO<>();
        try {
            target.setCreateTime(new Date());
            res = targetService.add(target);
            if (res > 0) {
                return responseObjectVO.getSuccessResponseVo(NormalConstants.SUCCESS);
            } else {
                return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
            }
        } catch (Exception e) {
            log.error("/target/add -> {}", e.getMessage());
            e.printStackTrace();
            return responseObjectVO.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
        }
    }

    @PostMapping(value = "/update")
    public ResponseObjectVO<String> update(@RequestParam String name, @RequestParam Integer id) {
        Integer res = null;
        ResponseObjectVO<String> responseObjectVO = new ResponseObjectVO<>();
        try {
            res = targetService.update(name, id);
            if (res > 0) {
                return responseObjectVO.getSuccessResponseVo(NormalConstants.SUCCESS);
            } else {
                return responseObjectVO.getSuccessResponseVo(NormalConstants.FAILED);
            }
        } catch (Exception e) {
            log.error("/target/update -> {}", e.getMessage());
            e.printStackTrace();
            return responseObjectVO.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
        }
    }

    @PostMapping(value = "/delete")
    public ResponseObjectVO<String> delete(@RequestParam Integer id) {
        ResponseObjectVO<String> responseObjectVO = new ResponseObjectVO<>();
        Integer res = null;
        try {
            res = targetService.delete(id);
            if (res > 0) {
                return responseObjectVO.getSuccessResponseVo(NormalConstants.SUCCESS);
            } else {
                return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
            }
        } catch (Exception e) {
            log.error("/target/delete -> {}", e.getMessage());
            e.printStackTrace();
            return responseObjectVO.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
        }
    }

    @GetMapping(value = "/findTargets")
    public ResponseObjectVO<List<Target>> findTargetsByUserId(@RequestParam Integer userId) {
        ResponseObjectVO<List<Target>> responseObjectVO = new ResponseObjectVO<>();
        List<Target> targets = null;
        try {
            targets = targetService.findByUserId(userId);
            return responseObjectVO.getSuccess(NormalConstants.SUCCESS, targets);
        } catch (Exception e) {
            log.error("/target/findTargets -> {}", e.getMessage());
            e.printStackTrace();
            return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
        }
    }
}
