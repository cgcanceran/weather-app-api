package com.tac.springweather.controller;

import com.google.common.collect.ImmutableList;
import com.tac.springweather.exception.BadRequestException;
import com.tac.springweather.model.location.AustraliaCityResponseModel;
import com.tac.springweather.service.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {
    @Mock
    private LocationService locationService;
    @InjectMocks
    private LocationController locationController;

    @Test
    public void testGetAusCities_Success() {
        List<AustraliaCityResponseModel> auCities = ImmutableList.of(AustraliaCityResponseModel.builder().build());
        Mockito.when(locationService.getAusCities(anyString(), anyString()))
                .thenReturn(auCities);
        Assertions.assertEquals(auCities,
                locationController.getAusCities(anyString(), anyString()));
        Assertions.assertEquals(1,
                locationController.getAusCities(anyString(), anyString()).size());
    }

    @Test
    public void testGetAusCities_Failed() {
        Mockito.when(locationService.getAusCities(anyString(), anyString()))
                .thenThrow(new BadRequestException());
        Assertions.assertThrows(BadRequestException.class,
                () -> locationController.getAusCities(anyString(), anyString()));
    }
}
