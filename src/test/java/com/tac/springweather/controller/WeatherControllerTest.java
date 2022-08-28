package com.tac.springweather.controller;

import com.tac.springweather.exception.BadRequestException;
import com.tac.springweather.model.forecast.ForecastResponseModel;
import com.tac.springweather.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {
    private static final String CLIENT_ID = "test_client_id";
    private static final String KEY = "test_eky";
    private static final Float LON = -23.33F;
    private static final Float LAT = 32.02F;

    @Mock
    private WeatherService weatherService;
    @InjectMocks
    private WeatherController weatherController;

    @Test
    public void testGetForecast_Success() {
        ForecastResponseModel forecastResponseModel = ForecastResponseModel.builder().build();
        Mockito.when(weatherService.getForecast(CLIENT_ID, KEY, LON, LAT))
                .thenReturn(Mono.just(forecastResponseModel));
        Assertions.assertEquals(forecastResponseModel,
                weatherController.getForecast(CLIENT_ID, KEY, LON, LAT).block());
    }

    @Test
    public void testGetForecast_Failed() {
        Mockito.when(weatherService.getForecast(CLIENT_ID, KEY, LON, LAT))
                .thenThrow(new BadRequestException());
        Assertions.assertThrows(BadRequestException.class,
                () -> weatherController.getForecast(CLIENT_ID, KEY, LON, LAT));
    }
}
