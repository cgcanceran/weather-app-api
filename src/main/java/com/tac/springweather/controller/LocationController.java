package com.tac.springweather.controller;

import com.tac.springweather.model.location.AustraliaCityResponseModel;
import com.tac.springweather.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/australia/cities")
    public List<AustraliaCityResponseModel> getAusCities(
            @RequestHeader(value = "ClientId") String clientId,
            @RequestHeader(value = "Key") String key
    ) {
        return locationService.getAusCities(clientId, key);
    }
}
