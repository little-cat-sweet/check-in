package com.hongyun.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.Constants.NormalConstants;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final Log log = LogFactory.get();

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getUsers")
    public ResponseObjectVO getUser() {
        ResponseObjectVO response = null;
        try {
            response = userService.findUsers();
        } catch (Exception e) {
            log.error("find users failed -> {}", e.getMessage());
            return response.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
        }
        return response;
    }
}
