package com.adidas.travel.service;

import com.adidas.travel.client.domain.Route;
import com.adidas.travel.client.service.OriginDestinyService;
import com.adidas.travel.domain.Itinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<Itinerary> listSortedByConnections(String iata) {

        List<Route> routes = originDestinyService.getRoutesByOrigin(iata);

        return routes
                .stream()
                .map(Itinerary::of)
                .sorted((o1, o2) -> {
                    Optional<List<Itinerary>> connectionsOpt1 = Optional.ofNullable(o1.getConnections());
                    Optional<List<Itinerary>> connectionsOpt2 = Optional.ofNullable(o2.getConnections());
                    if (!connectionsOpt1.isPresent() && !connectionsOpt2.isPresent()) {
                        return 0;
                    }
                    if (!connectionsOpt1.isPresent()) {
                        return -1;
                    }
                    if (!connectionsOpt2.isPresent()) {
                        return 1;
                    }
                    int cs1 = connectionsOpt1.get().size();
                    int cs2 = connectionsOpt2.get().size();
                    return Integer.compare(cs1, cs2);
                })
                .collect(Collectors.toList());
    }
}
