package com.hongyun.Constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "jwt-constants")
@Data
public class JwtConstants {

    private String secret;
    private Integer expiration;
}
