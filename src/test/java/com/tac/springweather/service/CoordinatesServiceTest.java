package com.tac.springweather.service;

import com.tac.springweather.exception.ForbiddenOperationException;
import com.tac.springweather.gateway.CoordinatesGateway;
import com.tac.springweather.helper.AuthorizationService;
import com.tac.springweather.model.geo.GeocodeResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class CoordinatesServiceTest {
    private static final String CLIENT_ID = "test_client_id";
    private static final String KEY = "test_eky";

    @Mock
    private CoordinatesGateway coordinatesGateway;
    @Mock
    private AuthorizationService authorizationService;
    @InjectMocks
    private CoordinatesService coordinatesService;

    @Test
    public void testGetCoordinates_Success() {
        String query = "query";
        GeocodeResponseModel geocodeResponseModel = GeocodeResponseModel.builder().build();
        Mockito.when(coordinatesGateway.getCoordinates(query))
                .thenReturn(Mono.just(geocodeResponseModel));
        Assertions.assertEquals(geocodeResponseModel,
                coordinatesService.getCoordinates(CLIENT_ID, KEY, query).block());
    }

    @Test
    public void testGetCoordinates_Forbidden() {
        Mockito.when(authorizationService.checkCredentials(CLIENT_ID, KEY))
                .thenThrow(new ForbiddenOperationException("Error"));
        Assertions.assertThrows(ForbiddenOperationException.class,
                () -> coordinatesService.getCoordinates(CLIENT_ID, KEY, "query"));
    }
}
