package com.hongyun.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import com.hongyun.entity.User;
import com.hongyun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final Log log = LogFactory.get();

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseObjectVO<String> register(@RequestBody User user) {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        String token = null;
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            token = userService.register(user);
            stopWatch.stop();
            log.info("/user/register execute time -> {}", stopWatch.getTotalTimeMillis());
            if (!StringUtils.hasLength(token)) {
                return response.getFailResponseVo("this account has registered !");
            }
        } catch (Exception e) {
            log.error("register failed -> {}", e.getMessage());
            return response.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
        }
        return response.getSuccess("register successfully !", token);
    }

    @PostMapping(value = "/login")
    public ResponseObjectVO<String> login(@RequestParam String email, @RequestParam String password) {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        String token = null;
        try {
            token = userService.login(email, password);
            if (Objects.isNull(token)) return response.getFailResponseVo("password is wrong");
        } catch (Exception e) {
            log.error("login failed -> {}", e.getMessage());
            return response.getFailResponseVo("login failed !");
        }
        return response.getSuccess("login successfully !", token);
    }
}
