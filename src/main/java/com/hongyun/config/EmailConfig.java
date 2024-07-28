package com.hongyun.config;


import cn.hutool.extra.mail.MailAccount;
import com.hongyun.constants.EmailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {

    @Autowired
    private EmailConstants emailConstants;

    @Bean
    public MailAccount getMailAccount(){
        MailAccount mailAccount = new MailAccount();
        mailAccount.setFrom(emailConstants.getFrom());
        mailAccount.setAuth(true);
        mailAccount.setPass(emailConstants.getPasscode());
        mailAccount.setHost(emailConstants.getHost());
        mailAccount.setUser(emailConstants.getFrom());
        return mailAccount;
    }
}
