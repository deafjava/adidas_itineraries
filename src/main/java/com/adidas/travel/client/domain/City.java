package com.adidas.travel.client.domain;

import lombok.Data;

@Data
public class City {

    private String iata;

    private String name;

    private Integer gmt;

    private State state;
}
