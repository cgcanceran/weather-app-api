package com.tac.springweather.gateway;

import com.tac.springweather.config.ServiceConfiguration;
import com.tac.springweather.exception.BadRequestException;
import com.tac.springweather.model.error.Errors;
import com.tac.springweather.model.location.CountriesResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CountryGateway {

    private final ServiceConfiguration serviceConfiguration;
    private final WebClient.Builder webClientBuilder;

    public Mono<CountriesResponseModel[]> getOceaniaCountries() {
         return webClientBuilder.build()
                .get()
                .uri(serviceConfiguration.getCountryEndpoint())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> response
                        .bodyToMono(Errors.class)
                        .map(errors -> new BadRequestException(Optional.ofNullable(errors)
                                .map(Errors::getErrors)
                                .map(err -> err.get(0))
                                .map(Errors.Error::getMsg)
                                .orElse("Failed to get countries."))))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(
                        new RuntimeException(response.statusCode().toString())))
                .bodyToMono(CountriesResponseModel[].class);
    }
}
