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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@RestController
@RequestMapping(value = "/user")
@Validated
public class UserController {
    private final Log log = LogFactory.get();

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping(value = "/register")
    public ResponseObjectVO<String> register(
            @RequestParam
            @NotBlank(message = "用户名不能为空")
            @Size(max = 30, message = "用户名长度不能超过30个字符")
            String name,
            @RequestParam
            @NotBlank(message = "邮箱不能为空")
            @Size(max = 30, message = "邮箱长度不能超过30个字符")
            String email,
            @RequestParam
            @NotBlank(message = "密码不能为空")
            @Size(max = 30, message = "密码长度不能超过30个字符")
            String password,
            @RequestParam
            @NotBlank(message = "验证码不能为空")
            @Size(max = 30, message = "验证码长度不能超过30个字符")
            String code
    ) throws Exception {

        ResponseObjectVO<String> response = new ResponseObjectVO<>();

        String codeFromRedis = stringRedisTemplate.opsForValue().get("email:" + email);
        if (!StringUtils.hasLength(codeFromRedis) || !codeFromRedis.equals(code)) {
            return response.getFailResponseVo("code 不正确或者已过期，请重试！");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        String token = userService.register(user);
        if (!StringUtils.hasLength(token)) {
            return response.getFailResponseVo("this account has registered !");
        }
        return response.getSuccess("register successfully !", token);
    }

    @PostMapping(value = "/login")
    public ResponseObjectVO<String> login(
            @RequestParam
            @NotBlank(message = "邮箱不能为空")
            @Size(max = 30, message = "邮箱长度不能超过30个字符")
            String email,
            @RequestParam
            @NotBlank(message = "密码不能为空")
            @Size(max = 30, message = "密码长度不能超过30个字符")
            String password
    ) throws Exception {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        String token = userService.login(email, password);
        if (Objects.isNull(token)) return response.getFailResponseVo("password is wrong");
        return response.getSuccess("login successfully !", token);
    }

    @GetMapping(value = "/code")
    public ResponseObjectVO<String> requestUpdatePasswordCode(
            @RequestParam
            @NotBlank(message = "邮箱不能为空")
            @Size(max = 30, message = "邮箱长度不能超过30个字符")
            String email
    ) {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        if (userService.checkCodeExisted(email)) {
            return response.getFailResponseVo("code 已经发送到您的邮箱，请查看重试，谢谢 !");
        }
        String code = userService.requestUpdatePasswordByEmail(email);
        log.info("code -> {}", code);
        return response.getSuccess("request success", NormalConstants.SUCCESS);
    }

    @GetMapping(value = "/updatePassword")
    public ResponseObjectVO<String> updatePassword(
            @RequestParam
            @NotBlank(message = "邮箱不能为空")
            @Size(max = 30, message = "邮箱长度不能超过30个字符")
            String email,
            @RequestParam
            @NotBlank(message = "验证码不能为空")
            @Size(max = 30, message = "验证码长度不能超过30个字符")
            String code,
            @RequestParam
            @NotBlank(message = "新密码不能为空")
            @Size(max = 30, message = "新密码长度不能超过30个字符")
            String newPassword
    ) throws Exception {
        ResponseObjectVO<String> response = new ResponseObjectVO<>();
        Boolean done = userService.updatePassword(email, code, newPassword);
        return done ? response.getSuccessResponseVo("update success") : response.getFailResponseVo("code or email is not valid");
    }

    @GetMapping("/avatar")
    public ResponseObjectVO<Avatar> getAvatarBase64() {
        ResponseObjectVO<Avatar> response = new ResponseObjectVO<>();
        byte[] avatar = userService.getAvatar();
        if (null == avatar) {
            return response.getFailResponseVo("no head photo");
        }
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

    @GetMapping(value = "/userInfo")
    public ResponseObjectVO<User> getUserInfo() {
        ResponseObjectVO<User> response = new ResponseObjectVO<>();
        return response.getSuccess(Constant.SUCCESS, userService.getUserInfo());
    }

    @PostMapping(value = "/update")
    public ResponseObjectVO<Boolean> update(
            @RequestParam
            @NotBlank(message = "用户名不能为空")
            @Size(max = 30, message = "用户名长度不能超过30个字符")
            String name,
            @RequestParam
            @NotBlank(message = "邮箱不能为空")
            @Size(max = 30, message = "邮箱长度不能超过30个字符")
            String email,
            @RequestParam(required = false) MultipartFile headImage
    ) throws IOException {

        ResponseObjectVO<Boolean> responseObjectVO = new ResponseObjectVO<>();
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        if (headImage != null && !headImage.isEmpty()) {
            user.setHeadImage(headImage.getBytes());
        }
        return responseObjectVO.getSuccess(Constant.SUCCESS, userService.update(user));
    }
}