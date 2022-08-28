package com.tac.springweather.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service.endpoints")
@Data
public class ServiceConfiguration {
    private String geocodeEndpoint;
    private String weatherEndpoint;
    private String countryEndpoint;

}
