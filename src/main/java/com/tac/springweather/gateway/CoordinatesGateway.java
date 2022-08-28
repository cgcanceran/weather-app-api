package com.tac.springweather.gateway;

import com.tac.springweather.config.GeocodeConfiguration;
import com.tac.springweather.config.ServiceConfiguration;
import com.tac.springweather.exception.BadRequestException;
import com.tac.springweather.model.error.Errors;
import com.tac.springweather.model.geo.GeocodeResponseModel;
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
public class CoordinatesGateway {
    private static final String LOCATION = "location";
    private static final String KEY = "key";

    private final GeocodeConfiguration geocodeConfiguration;
    private final ServiceConfiguration serviceConfiguration;
    private final WebClient.Builder webClientBuilder;

    public Mono<GeocodeResponseModel> getCoordinates(String query) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put(LOCATION, query);
        pathVariables.put(KEY, geocodeConfiguration.getKey());

         return webClientBuilder.build()
                .get()
                .uri(UriComponentsBuilder.fromHttpUrl(serviceConfiguration.getGeocodeEndpoint()).buildAndExpand(pathVariables).toUri())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> response
                        .bodyToMono(Errors.class)
                        .map(errors -> new BadRequestException(Optional.ofNullable(errors)
                                .map(Errors::getErrors)
                                .map(err -> err.get(0))
                                .map(Errors.Error::getMsg)
                                .orElse("Failed to get coordinates."))))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(
                        new RuntimeException(response.statusCode().toString())))
                .bodyToMono(GeocodeResponseModel.class);
    }
}
