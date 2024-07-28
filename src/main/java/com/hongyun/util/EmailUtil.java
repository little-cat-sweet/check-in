package com.hongyun.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class EmailUtil {

    Log log = Log.get();

    @Autowired
    private MailAccount mailAccount;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String sendUpdatePasswordCode(String requestEmail){
        String code = RandomUtil.randomString(6);
        try {
            MailUtil.send(
                    mailAccount,
                    requestEmail,
                    "password change request code",
                    code,
                    false);
        } catch (Exception e) {
            log.error("send email failed -> {}", e.getMessage());
            throw new RuntimeException(e);
        }
        stringRedisTemplate.opsForValue().set("email:" + requestEmail, code, 5, TimeUnit.MINUTES);
        return code;
    }
}
