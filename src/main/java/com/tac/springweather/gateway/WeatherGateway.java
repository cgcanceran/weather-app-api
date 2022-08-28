package com.tac.springweather.gateway;

import com.tac.springweather.config.ServiceConfiguration;
import com.tac.springweather.config.WeatherConfiguration;
import com.tac.springweather.exception.BadRequestException;
import com.tac.springweather.model.error.Errors;
import com.tac.springweather.model.forecast.ForecastResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherGateway {
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String APP_ID = "appid";

    private final WeatherConfiguration weatherConfiguration;
    private final ServiceConfiguration serviceConfiguration;

    private final WebClient.Builder webClientBuilder;

    public Mono<ForecastResponseModel> getForecast(float lat, float lon) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put(LAT, Float.toString(lat));
        pathVariables.put(LON, Float.toString(lon));
        pathVariables.put(APP_ID, weatherConfiguration.getAppid());

        return webClientBuilder.build()
                .get()
                .uri(UriComponentsBuilder.fromHttpUrl(serviceConfiguration.getWeatherEndpoint()).buildAndExpand(pathVariables).toUri())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> response
                        .bodyToMono(Errors.class)
                        .map(errors -> new BadRequestException(Optional.ofNullable(errors)
                                .map(Errors::getErrors)
                                .map(err -> err.get(0))
                                .map(Errors.Error::getMsg)
                                .orElse("Failed to get forecast."))))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(
                        new RuntimeException(response.statusCode().toString())))
                .bodyToMono(ForecastResponseModel.class);
    }
}
