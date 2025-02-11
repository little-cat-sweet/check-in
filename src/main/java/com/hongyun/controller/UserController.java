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
    public ResponseObjectVO<String> register(@RequestBody User user) throws Exception {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        String token = null;
        String validMsg = validationUtil.checkRegisterUserParams(user);
        if (StringUtils.hasLength(validMsg)) {
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
        return response.getSuccess("register successfully !", token);
    }

    @PostMapping(value = "/login")
    public ResponseObjectVO<String> login(@RequestParam String email, @RequestParam String password) throws Exception {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        String token = null;
        token = userService.login(email, password);
        if (Objects.isNull(token)) return response.getFailResponseVo("password is wrong");
        return response.getSuccess("login successfully !", token);
    }

    @GetMapping(value = "/code")
    public ResponseObjectVO<String> requestUpdatePasswordCode(@RequestParam String email) {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        String code = null;
        if (userService.checkCodeExisted(email)) {
            return response.getFailResponseVo("this email update password code has sent to your email, pls try it later. Thanks !");
        }
        code = userService.requestUpdatePasswordByEmail(email);
        log.info("code -> {}", code);
        return response.getSuccess("request success", NormalConstants.SUCCESS);
    }

    @GetMapping(value = "/updatePassword")
    public ResponseObjectVO<String> updatePassword(@RequestParam String email, @RequestParam String code, @RequestParam String newPassword) throws Exception {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        Boolean done = null;
        done = userService.updatePassword(email, code, newPassword);
        return done ? response.getSuccessResponseVo("update success") : response.getFailResponseVo("code or email is not valid");
    }
}
