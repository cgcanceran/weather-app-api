package com.tac.springweather.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AustraliaCapitalCitiesEnum {
    BRISBANE("Brisbane"),
    CANBERRA("Canberra"),
    DARWIN("Darwin"),
    HOBART("Hobart"),
    MELBOURNE("Melbourne"),
    PERTH("Perth"),
    SYDNEY("Sydney");

    private String city;
}
