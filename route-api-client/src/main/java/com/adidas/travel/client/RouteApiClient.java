package com.adidas.travel.client;

import com.adidas.travel.domain.Route;

import java.util.List;

public interface RouteApiClient {

    List<Route> getRoutes(String iata);

}
