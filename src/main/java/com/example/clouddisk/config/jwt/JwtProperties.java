package com.example.clouddisk.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author R.Q.
 * brief:JWT 配置类
 */
@Data
@Component
@ConfigurationProperties(prefix="jwt")
public class JwtProperties {
    private String secret;
    private JwtHeader header;
    private JwtPayload payload;
}
