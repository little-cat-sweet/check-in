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

    public String sendUpdatePasswordCode(String requestEmail) {
        String code = RandomUtil.randomString(6);

        String htmlContent = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px; overflow: hidden;'>"
                + "<div style='background-color: #007BFF; padding: 20px; text-align: center;'>"
                + "<h2 style='color: white; margin: 0;'>密码验证码</h2>"
                + "</div>"
                + "<div style='padding: 20px; background-color: #f9f9f9;'>"
                + "<p>您好，</p>"
                + "<p>我们收到您的请求，正在尝试重置您的账户密码。请使用以下验证码完成操作：</p>"
                + "<div style='text-align: center; margin: 20px 0;'>"
                + "<span style='display: inline-block; font-size: 24px; font-weight: bold; color: #007BFF; border: 2px solid #007BFF; border-radius: 4px; padding: 10px 20px;'>"
                + code
                + "</span>"
                + "</div>"
                + "<p>该验证码将在5分钟内有效，请勿将其分享给他人。</p>"
                + "<p>如果您未请求验证码，请忽略此邮件。</p>"
                + "<p>祝您生活愉快！</p>"
                + "<p>此致，<br>您的团队</p>"
                + "</div>"
                + "<div style='background-color: #f1f1f1; text-align: center; padding: 10px; font-size: 12px; color: #888;'>"
                + "&copy; 2025 打卡小工具. 保留所有权利。"
                + "</div>"
                + "</div>";

        try {
            // 使用 HTML 内容发送邮件
            MailUtil.send(mailAccount, requestEmail, "密码请求验证码", htmlContent, true);
        } catch (Exception e) {
            log.error("发送邮件失败 -> {}", e.getMessage());
            throw new RuntimeException("邮件发送失败", e);
        }

        // 将验证码存入 Redis，设置 5 分钟过期时间
        stringRedisTemplate.opsForValue().set("email:" + requestEmail, code, 5, TimeUnit.MINUTES);
        return code;
    }
}