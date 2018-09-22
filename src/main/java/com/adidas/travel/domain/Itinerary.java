package com.adidas.travel.domain;

import com.adidas.travel.client.domain.Connection;
import com.adidas.travel.client.domain.Route;
import com.adidas.travel.service.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Itinerary {

    private String originIata;

    private String originCity;

    private String originCountry;

    private LocalTime originGmtTime;

    private LocalTime originLocalTime;

    private String destinyIata;

    private String destinyCity;

    private String destinyCountry;

    private LocalTime destinyGmtTime;

    private LocalTime destinyLocalTime;

    private LocalTime duration;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<Itinerary> connections;

    public static Itinerary of(Route route) {
        Itinerary itinerary = new Itinerary();

        itinerary.setOriginIata(route.getOriginCity().getIata());
        itinerary.setOriginCity(route.getOriginCity().getName());
        itinerary.setOriginCountry(route.getOriginCity().getState().getCountry().getIsoCode());
        itinerary.setOriginGmtTime(route.getDepartureTime().plusHours(route.getOriginCity().getGmt()));
        itinerary.setOriginLocalTime(route.getDepartureTime());

        itinerary.setDestinyIata(route.getDestinyCity().getIata());
        itinerary.setDestinyCity(route.getDestinyCity().getName());
        itinerary.setDestinyCountry(route.getDestinyCity().getState().getCountry().getIsoCode());
        itinerary.setDestinyGmtTime(route.getArrivalTime().plusHours(route.getDestinyCity().getGmt()));
        itinerary.setDestinyLocalTime(route.getArrivalTime());

        Duration d = Duration.between(route.getDepartureTime(), route.getArrivalTime());
        if(d.isNegative()) {
            d = d.plusHours(24);
        }

        itinerary.setDuration(LocalTime.parse(TimeUtils.parse(d)));
        Optional<List<Connection>> connectionsOpt = Optional.ofNullable(route.getConnections());

        connectionsOpt.ifPresent(cs -> {
            List<Itinerary> itinerariesConnections = cs
                    .stream()
                    .map(c -> of(c.getConnectionRoute()))
                    .collect(Collectors.toList());
            itinerary.setConnections(itinerariesConnections);
        });

        return itinerary;
    }
}
