package com.tac.springweather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "integration.forecast")
@Data
public class WeatherConfiguration {
    private String appid;
}
