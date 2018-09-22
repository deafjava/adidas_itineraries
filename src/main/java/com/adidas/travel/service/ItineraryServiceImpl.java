package com.adidas.travel.service;

import com.adidas.travel.client.domain.Route;
import com.adidas.travel.client.service.OriginDestinyService;
import com.adidas.travel.domain.Itinerary;
import com.adidas.travel.service.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    @Autowired
    private OriginDestinyService originDestinyService;

    @Override
    public List<Itinerary> listSortedByTime(String iata) {

        List<Route> routes = originDestinyService.getRoutesByOrigin(iata);

        return routes
                .stream()
                .map(Itinerary::of)
                .sorted(Comparator.comparing(Itinerary::getDuration))
                .collect(Collectors.toList());
    }
}
