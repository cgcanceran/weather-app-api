package com.tac.springweather.service;

import com.tac.springweather.gateway.CoordinatesGateway;
import com.tac.springweather.helper.AuthorizationService;
import com.tac.springweather.model.geo.GeocodeResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CoordinatesService {
    private final CoordinatesGateway coordinatesGateway;
    private final AuthorizationService authorizationService;

    public Mono<GeocodeResponseModel> getCoordinates(String clientId, String key, String location) {
        authorizationService.checkCredentials(clientId, key);
        return coordinatesGateway.getCoordinates(location);
    }
}
