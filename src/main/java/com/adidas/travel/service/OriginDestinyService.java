package com.adidas.travel.service;


import com.adidas.travel.domain.Route;

import java.util.List;

public interface OriginDestinyService {

    List<Route> getRoutesByOrigin(String iata);

}
