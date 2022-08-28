package com.tac.springweather.model.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tac.springweather.model.geo.GeocodeResponseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CountriesResponseModel {
    private Name name;
    private List<Float> latlng;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Name {
        private String common;
        private String official;
    }
}
