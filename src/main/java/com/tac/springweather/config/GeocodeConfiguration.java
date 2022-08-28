package com.tac.springweather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "integration.geocode")
@Data
public class GeocodeConfiguration {
    private String key;
}
