package com.adidas.travel.client.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class Route {

    private City originCity;

    private City destinyCity;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Connection> connections;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalTime durationTime;
}