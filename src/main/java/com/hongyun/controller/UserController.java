package com.hongyun.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.Constants.NormalConstants;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.entity.User;
import com.hongyun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final Log log = LogFactory.get();

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getUsers")
    public ResponseObjectVO<List<User>> getUser() {
        ResponseObjectVO<List<User>> response = new ResponseObjectVO<>();
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            response = userService.findUsers();
            stopWatch.stop();
            log.info("/user/getUsers time -> {}", stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            log.error("find users failed -> {}", e.getMessage());
            return response.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
        }
        return response;
    }

    @PostMapping(value = "/register")
    public ResponseObjectVO<String> register(@RequestBody User user){
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        String token = null;
        try {
            token = userService.register(user);
        } catch (Exception e) {
            log.error("register failed -> {}", e.getMessage());
            return response.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
        }
        return response.getSuccess("register successfully !", token);
    }
}
