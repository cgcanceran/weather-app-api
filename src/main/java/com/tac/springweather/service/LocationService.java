package com.tac.springweather.service;

import com.tac.springweather.enums.AustraliaCapitalCitiesEnum;
import com.tac.springweather.helper.AuthorizationService;
import com.tac.springweather.model.location.AustraliaCityResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final AuthorizationService authorizationService;

    public List<AustraliaCityResponseModel> getAusCities(String clientId, String key) {
        authorizationService.checkCredentials(clientId, key);
        List<AustraliaCityResponseModel> auCities = new ArrayList<>();
        EnumSet.allOf(AustraliaCapitalCitiesEnum.class)
                .forEach(city -> auCities.add(AustraliaCityResponseModel.builder()
                        .city(city.getCity()).build()));
        return auCities;
    }
}
