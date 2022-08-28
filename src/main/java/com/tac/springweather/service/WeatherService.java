package com.tac.springweather.service;

import com.tac.springweather.gateway.WeatherGateway;
import com.tac.springweather.helper.AuthorizationService;
import com.tac.springweather.model.forecast.ForecastResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class WeatherService {
    private final WeatherGateway weatherGateway;
    private final AuthorizationService authorizationService;

    public Mono<ForecastResponseModel> getForecast(String clientId, String key, float lat, float lon) {
        authorizationService.checkCredentials(clientId, key);
        return weatherGateway.getForecast(lat, lon);
    }
}
