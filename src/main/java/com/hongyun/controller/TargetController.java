package com.hongyun.controller;

import cn.hutool.log.Log;
import com.hongyun.annotation.TimeLog;
import com.hongyun.common.PageVO;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import com.hongyun.dto.vo.User;
import com.hongyun.entity.Target;
import com.hongyun.service.TargetService;
import com.hongyun.util.DateUtil;
import com.hongyun.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@TimeLog
@RestController
@RequestMapping(value = "/target")
@Validated
public class TargetController {

    private final Log log = Log.get();

    @Autowired
    private TargetService targetService;

    @Autowired
    private DateUtil dateUtil;

    @PostMapping(value = "/add")
    @TimeLog(value = "add target")
    public ResponseObjectVO<String> addTarget(@Valid @RequestBody Target target) {
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
    public ResponseObjectVO<String> update(
            @RequestParam
            @NotBlank(message = "名称不能为空")
            @Size(max = 30, message = "名称长度不能超过30个字符")
            String name,
            @RequestParam
            @NotNull(message = "ID不能为空")
            Integer id) {
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
    public ResponseObjectVO<String> delete(
            @RequestParam
            @NotNull(message = "ID不能为空")
            Integer id) {
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
    public ResponseObjectVO<List<Target>> findTargetsByUserId(
            @RequestParam
            @NotNull(message = "页码不能为空")
            Integer pageNum,
            @RequestParam
            @NotNull(message = "每页条数不能为空")
            Integer pageSize) {
        ResponseObjectVO<List<Target>> responseObjectVO = new ResponseObjectVO<>();
        PageVO pageVO = new PageVO();
        pageVO.setPageNum(pageNum);
        pageVO.setPageSize(pageSize);
        List<Target> targets = null;
        targets = targetService.findByUserId(Math.toIntExact(UserHolder.getUser().getId()), pageVO);
        responseObjectVO.setPagination(pageVO);
        return responseObjectVO.getSuccess(NormalConstants.SUCCESS, targets);

    }
}