package com.adidas.travel;

import com.adidas.travel.domain.Route;

import java.util.ArrayList;
import java.util.List;

import static com.adidas.travel.RouteTestFactory.*;

public class RouteListTestFactory {

    public static List<Route> messedUpOrderRouteList() {

        List<Route> routes = new ArrayList<>();

        routes.add(routeTime5Connections2());
        routes.add(routeTime3Connections1());
        routes.add(routeTime6Connections0());
        routes.add(routeTime1Connections3());
        routes.add(routeTime4Connections0());
        routes.add(routeTime2Connections0());

        return routes;
    }

    public static List<Route> byConnectionsOrderedRouteList() {

        List<Route> routes = new ArrayList<>();

        routes.add(routeTime2Connections0());
        routes.add(routeTime6Connections0());
        routes.add(routeTime4Connections0());
        routes.add(routeTime3Connections1());
        routes.add(routeTime5Connections2());
        routes.add(routeTime1Connections3());

        return routes;
    }

    public static List<Route> byTimeOrderedRouteList() {

        List<Route> routes = new ArrayList<>();

        routes.add(routeTime1Connections3());
        routes.add(routeTime2Connections0());
        routes.add(routeTime3Connections1());
        routes.add(routeTime4Connections0());
        routes.add(routeTime5Connections2());
        routes.add(routeTime6Connections0());

        return routes;
    }
}
