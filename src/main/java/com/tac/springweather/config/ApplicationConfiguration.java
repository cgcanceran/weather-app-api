package com.tac.springweather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.credentials")
@Data
public class ApplicationConfiguration {
    private String clientId;
    private String clientSecret;
}
