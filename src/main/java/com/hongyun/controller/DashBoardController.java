package com.hongyun.controller;

import com.hongyun.common.ResponseObjectVO;
import com.hongyun.entity.DashTargetItem;
import com.hongyun.service.DashBoardService;
import com.hongyun.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/dash")
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping(value = "/getDashTargetItems")
    public ResponseObjectVO<List<DashTargetItem>> getDashTargetItems(){
        return dashBoardService.getDashTargetItems(Math.toIntExact(UserHolder.getUser().getId()));
    }
}
