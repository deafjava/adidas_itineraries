package com.adidas.travel.domain;

import lombok.Data;

@Data
public class State {

    private String name;

    private Country country;

    private String isoCode;
}
