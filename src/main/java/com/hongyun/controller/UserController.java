package com.hongyun.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.common.Constant;
import com.hongyun.common.ResponseObjectVO;
import com.hongyun.constants.NormalConstants;
import com.hongyun.dto.Avatar;
import com.hongyun.entity.User;
import com.hongyun.service.UserService;
import com.hongyun.util.ValidationUtil;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
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
    public ResponseObjectVO<String> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) MultipartFile headImage) throws Exception {

        ResponseObjectVO<String> response = new ResponseObjectVO<>();

        if (!StringUtils.hasLength(name)) {
            return response.getFailResponseVo("name is required");
        }
        if (!StringUtils.hasLength(email)) {
            return response.getFailResponseVo("email is required");
        }
        if (!StringUtils.hasLength(password)) {
            return response.getFailResponseVo("password is required");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        if (headImage != null && !headImage.isEmpty()) {
            user.setHeadImage(headImage.getBytes());
        }

        String token = userService.register(user);
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

    @GetMapping("/avatar")
    public ResponseObjectVO<Avatar> getAvatarBase64() {
        ResponseObjectVO<Avatar> response = new ResponseObjectVO<>();
        byte[] avatar = userService.getAvatar();
        String mimeType = detectMimeType(avatar);
        String base64 = Base64.getEncoder().encodeToString(avatar);

        Avatar res = new Avatar();
        res.setBase64(base64);
        res.setMimeType(mimeType);

        return response.getSuccess(Constant.SUCCESS, res);
    }

    private String detectMimeType(byte[] bytes) {
        Tika tika = new Tika();
        return tika.detect(bytes);
    }
}
