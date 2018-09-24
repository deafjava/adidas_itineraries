package com.adidas.travel.service;

import com.adidas.travel.RouteTestFactory;
import com.adidas.travel.client.RouteApiClient;
import com.adidas.travel.domain.Route;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

public class OriginDestinyServiceImplTest {

    private OriginDestinyService originDestinyService;

    private RouteApiClient routeApiClient;

    @BeforeClass
    public void setUp() {
        routeApiClient = mock(RouteApiClient.class);
        originDestinyService = new OriginDestinyServiceImpl(routeApiClient);
    }

    @Test
    public void shouldReturnFullListTest() {

        String iata = "ZAZ";

        List<Route> routes = new ArrayList<>();

        Route sampleRoute = RouteTestFactory.createMonoRoute();

        routes.add(sampleRoute);
        routes.add(sampleRoute);
        routes.add(sampleRoute);

        when(routeApiClient.getRoutes(iata)).thenReturn(routes);

        List<Route> r = originDestinyService.getRoutesByOrigin(iata);

        assertTrue(r.contains(sampleRoute));
    }

    @Test
    public void shouldReturnEmptyListTest() {

        String iata = "ZAZ";

        List<Route> routes = new ArrayList<>();

        when(routeApiClient.getRoutes(iata)).thenReturn(routes);

        List<Route> r = originDestinyService.getRoutesByOrigin(iata);

        assertTrue(r.isEmpty());
    }
}