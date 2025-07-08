package com.hongyun.config;

import cn.hutool.extra.mail.MailAccount;
import com.hongyun.constants.EmailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailConstants emailConstants;

    @Bean
    public MailAccount mailAccount() {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(emailConstants.getHost());
        mailAccount.setPort(emailConstants.getPort());
        mailAccount.setAuth(true);
        mailAccount.setUser(emailConstants.getUser());
        mailAccount.setPass(emailConstants.getPasscode());
        mailAccount.setFrom(emailConstants.getFrom());
        mailAccount.setSslEnable(emailConstants.getSslEnable());
        mailAccount.setSocketFactoryClass(emailConstants.getSocketFactoryClass());
        return mailAccount;
    }
}