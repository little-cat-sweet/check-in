package com.hongyun.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "email")
@Data
public class EmailConstants {

    private String host;
    private String from;
    private String user;
    private String passcode;
}
