package com.adidas.travel.service;

import com.adidas.travel.client.RouteApiClient;
import com.adidas.travel.domain.Route;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OriginDestinyServiceImpl implements OriginDestinyService {

    @Autowired
    private RouteApiClient client;

    @Override
    public List<Route> getRoutesByOrigin(String iata) {

        return client.getRoutes(iata);

    }


}
