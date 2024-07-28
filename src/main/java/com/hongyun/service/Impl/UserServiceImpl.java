package com.hongyun.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.hongyun.constants.RedisConstants;
import com.hongyun.entity.User;
import com.hongyun.mapper.UserMapper;
import com.hongyun.service.UserService;
import com.hongyun.util.EmailUtil;
import com.hongyun.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private PasswordUtils passwordUtils;
    private Log log = LogFactory.get();


    @Override
    @Transactional
    public String register(User user) throws Exception {

        if (isUserExisted(user.getEmail())) return null;
        user.setPassword(passwordUtils.encrypt(user.getPassword()));
        if (userMapper.insert(user) > 0) {
            return getTokenAndInitUserInfoInRedis(user);

        }
        return null;
    }

    @Override
    public String login(String email, String password) throws Exception {
        User user = userMapper.findByEmail(email);
        if (! Objects.isNull(user) && passwordUtils.decrypt(user.getPassword()).equals(password)) {
            return getTokenAndInitUserInfoInRedis(user);
        }
        return null;
    }

    private boolean isUserExisted(String email) {
        User user = userMapper.findByEmail(email);
        return !Objects.isNull(user);
    }

    public String getTokenAndInitUserInfoInRedis(User user) {
        String token = null;
        com.hongyun.dto.vo.User userVo = new com.hongyun.dto.vo.User();
        userVo.setName(user.getName());
        userVo.setEmail(user.getEmail());
        token = UUID.fastUUID().toString(true);
        userVo.setToken(token);
        Map<String, Object> userMap = BeanUtil.beanToMap(userVo, new HashMap<>(), CopyOptions.create().setIgnoreNullValue(true)
                .setFieldValueEditor((fieldName, fieldValue) -> fieldValue == null ? "" : fieldValue.toString()));
        stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_CODE_TOKEN + token, userMap);
        stringRedisTemplate.expire(RedisConstants.LOGIN_CODE_TOKEN + token, Duration.ofHours(1));
        return token;
    }

    @Override
    public String requestUpdatePasswordByEmail(String email) {
        String code = null;

        try {
            code = emailUtil.sendUpdatePasswordCode(email);
        } catch (Exception e) {
            log.error("requestUpdatePasswordByEmail -> {}", e.getMessage());
        }
        log.info("email -> {}, code -> {}", email, code);
        return code;
    }

    @Override
    public boolean checkCodeExisted(String email) {

        String code = stringRedisTemplate.opsForValue().get("email:" + email);
        return StringUtils.hasLength(code);
    }

    @Override
    public boolean updatePassword(String email, String code, String newPassword) throws Exception {
        String codeFromRedis = stringRedisTemplate.opsForValue().get("email:" + email);
        if (! StringUtils.hasLength(codeFromRedis) || !codeFromRedis.equals(code)) {
            return false;
        }
        User user = userMapper.findByEmail(email);
        if (user != null) {
            user.setPassword(passwordUtils.encrypt(newPassword));
            return userMapper.updatePassword(user) > 0;

        }
        return false;
    }
}
