package com.hongyun.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import com.hongyun.entity.User;
import com.hongyun.service.UserService;
import com.hongyun.util.ValidationUtil;
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

    @Autowired
    private ValidationUtil validationUtil;

    @PostMapping(value = "/register")
    public ResponseObjectVO<String> register(@RequestBody User user) {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        String token = null;
        try {
            String validMsg = validationUtil.checkRegisterUserParams(user);
            if(StringUtils.hasLength(validMsg)){
                return response.getFailResponseVo(validMsg);
            }
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
            e.printStackTrace();
            return response.getFailResponseVo("login failed !");
        }
        return response.getSuccess("login successfully !", token);
    }

    @GetMapping(value = "/code")
    public ResponseObjectVO<String> requestUpdatePasswordCode(@RequestParam String email) {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        String code = null;
        try {
            if (userService.checkCodeExisted(email)) {
                return response.getFailResponseVo("this email update password code has sent to your email, pls try it later. Thanks !");
            }
            code = userService.requestUpdatePasswordByEmail(email);
        } catch (Exception e) {
            log.error("request code error -> {}", e.getMessage());
            return response.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
        }
        return response.getSuccess("request success", NormalConstants.SUCCESS);
    }

    @GetMapping(value = "/updatePassword")
    public ResponseObjectVO<String> updatePassword(@RequestParam String email, @RequestParam String code, @RequestParam String newPassword) {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        Boolean done = null;
        try {
            done = userService.updatePassword(email, code, newPassword);
        } catch (Exception e) {
            log.error("updatePassword error -> {}", e.getMessage());
            e.printStackTrace();
            return response.getFailResponseVo(NormalConstants.ERROR_MESSAGE);
        }
        return done ? response.getSuccessResponseVo("update success") : response.getFailResponseVo("code or email is not valid");
    }
}
