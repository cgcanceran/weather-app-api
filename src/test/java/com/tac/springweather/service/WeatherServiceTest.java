package com.tac.springweather.service;

import com.tac.springweather.exception.ForbiddenOperationException;
import com.tac.springweather.gateway.WeatherGateway;
import com.tac.springweather.helper.AuthorizationService;
import com.tac.springweather.model.forecast.ForecastResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
    private static final String CLIENT_ID = "test_client_id";
    private static final String KEY = "test_eky";
    private static final Float LON = -32.11F;
    private static final Float LAT =  25.11F;

    @Mock
    private WeatherGateway weatherGateway;
    @Mock
    private AuthorizationService authorizationService;
    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void testGetForecast_Success() {
        ForecastResponseModel forecastResponseModel = ForecastResponseModel.builder().build();
        Mockito.when(weatherGateway.getForecast(LAT, LON))
                .thenReturn(Mono.just(forecastResponseModel));
        Assertions.assertEquals(forecastResponseModel,
                weatherService.getForecast(CLIENT_ID, KEY, LAT, LON).block());
    }

    @Test
    public void testGetForecast_Failed() {
        Mockito.when(authorizationService.checkCredentials(CLIENT_ID, KEY))
                .thenThrow(new ForbiddenOperationException("Error"));
        Assertions.assertThrows(ForbiddenOperationException.class,
                () -> weatherService.getForecast(CLIENT_ID, KEY, LON, LAT).block());
    }
}
