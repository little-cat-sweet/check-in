package com.hongyun.controller;

import cn.hutool.log.Log;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import com.hongyun.dto.vo.User;
import com.hongyun.entity.Target;
import com.hongyun.service.TargetService;
import com.hongyun.util.DateUtil;
import com.hongyun.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/target")
public class TargetController {

    private final Log log = Log.get();

    @Autowired
    private TargetService targetService;

    @Autowired
    private DateUtil dateUtil;

    @PostMapping(value = "/add")
    public ResponseObjectVO<String> addTarget(@RequestBody Target target) {
        Integer res = null;
        ResponseObjectVO<String> responseObjectVO = new ResponseObjectVO<>();
        User user = UserHolder.getUser();
        target.setUserId(Math.toIntExact(user.getId()));
        target.setCreateTime(dateUtil.getYYYY_MM_DD_DateByNow());
        res = targetService.add(target);
        if (res > 0) {
            return responseObjectVO.getSuccessResponseVo(NormalConstants.SUCCESS);
        } else {
            return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
        }
    }

    @PostMapping(value = "/update")
    public ResponseObjectVO<String> update(@RequestParam String name, @RequestParam Integer id) {
        Integer res = null;
        ResponseObjectVO<String> responseObjectVO = new ResponseObjectVO<>();
        res = targetService.update(name, id);
        if (res > 0) {
            return responseObjectVO.getSuccessResponseVo(NormalConstants.SUCCESS);
        } else {
            return responseObjectVO.getSuccessResponseVo(NormalConstants.FAILED);
        }
    }

    @PostMapping(value = "/delete")
    public ResponseObjectVO<String> delete(@RequestParam Integer id) {
        ResponseObjectVO<String> responseObjectVO = new ResponseObjectVO<>();
        Integer res = null;
        res = targetService.delete(id);
        if (res > 0) {
            return responseObjectVO.getSuccessResponseVo(NormalConstants.SUCCESS);
        } else {
            return responseObjectVO.getFailResponseVo(NormalConstants.FAILED);
        }
    }

    @GetMapping(value = "/findTargets")
    public ResponseObjectVO<List<Target>> findTargetsByUserId() {
        ResponseObjectVO<List<Target>> responseObjectVO = new ResponseObjectVO<>();
        List<Target> targets = null;
        targets = targetService.findByUserId(Math.toIntExact(UserHolder.getUser().getId()));
        return responseObjectVO.getSuccess(NormalConstants.SUCCESS, targets);

    }

    @GetMapping(value = "/test1")
    public void test1(@RequestParam int number) {
        int res = 1 / number;
        System.out.println(res);
    }
}
