package com.tac.springweather.controller;

import com.tac.springweather.exception.BadRequestException;
import com.tac.springweather.model.geo.GeocodeResponseModel;
import com.tac.springweather.service.CoordinatesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class CoordinatesControllerTest {

    @Mock
    private CoordinatesService coordinatesService;
    @InjectMocks
    private CoordinatesController coordinatesController;

    @Test
    public void testGetCoordinates_success() {
        GeocodeResponseModel geocodeResponseModel = GeocodeResponseModel.builder().build();
        Mockito.when(coordinatesService.getCoordinates(anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(geocodeResponseModel));
        Assertions.assertEquals(geocodeResponseModel,
                coordinatesController.getCoordinates(anyString(), anyString(), anyString()).block());
    }

    @Test
    public void testGetCoordinates_Failed() {
        Mockito.when(coordinatesService.getCoordinates(anyString(), anyString(), anyString()))
                .thenThrow(new BadRequestException());
        Assertions.assertThrows(BadRequestException.class,
                () -> coordinatesController.getCoordinates(anyString(), anyString(), anyString()));
    }
}
