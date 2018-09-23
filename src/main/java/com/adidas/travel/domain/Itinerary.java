package com.adidas.travel.domain;

import com.adidas.travel.client.domain.Connection;
import com.adidas.travel.client.domain.Route;
import com.adidas.travel.service.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Itinerary {

    private AirportDetail origin;

    private AirportDetail destiny;

    private LocalTime duration;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<Itinerary> connections;

    public static Itinerary of(Route route) {
        Itinerary itinerary = new Itinerary();

        itinerary.setOrigin(AirportDetail.of(route.getOriginCity(), route.getDepartureTime()));

        itinerary.setDestiny(AirportDetail.of(route.getDestinyCity(), route.getArrivalTime()));

        Duration d = Duration.between(route.getDepartureTime(), route.getArrivalTime());
        if (d.isNegative()) {
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

    @JsonGetter("duration")
    public String getRawDuration() {
        return duration.toString();
    }
}
