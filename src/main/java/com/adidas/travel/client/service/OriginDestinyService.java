package com.adidas.travel.client.service;

import com.adidas.travel.client.domain.Route;

import java.util.List;

public interface OriginDestinyService {

    List<Route> getRoutesByOrigin(String iata);

}
