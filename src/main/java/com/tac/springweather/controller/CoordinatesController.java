package com.tac.springweather.controller;

import com.tac.springweather.model.geo.GeocodeResponseModel;
import com.tac.springweather.service.CoordinatesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CoordinatesController {

    private final CoordinatesService coordinatesService;
    @GetMapping("/geocode/{location}")
    public Mono<GeocodeResponseModel> getCoordinates(
            @RequestHeader(value = "ClientId") String clientId,
            @RequestHeader(value = "Key") String key,
            @PathVariable String location) {
        return coordinatesService.getCoordinates(clientId, key, location);
    }
}
