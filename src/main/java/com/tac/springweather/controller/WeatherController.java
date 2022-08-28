package com.tac.springweather.controller;

import com.tac.springweather.model.forecast.ForecastResponseModel;
import com.tac.springweather.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/forecast")
    public Mono<ForecastResponseModel> getForecast( @RequestHeader(value = "ClientId") String clientId,
                                                    @RequestHeader(value = "Key") String key,
                                                    @RequestParam float lat,
                                                    @RequestParam float lon){
        return weatherService.getForecast(clientId, key, lat, lon);
    }

}
