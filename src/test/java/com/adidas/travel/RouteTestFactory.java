package com.adidas.travel;

import com.adidas.travel.domain.*;
import com.adidas.travel.service.utils.TimeUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RouteTestFactory {

    public static Route createMonoRoute() {
        Route route = new Route();
        route.setDepartureTime(LocalTime.of(1, 1, 1));
        route.setArrivalTime(LocalTime.of(3, 3, 3));
        Country cy = new Country();
        cy.setName("Spain");
        cy.setIsoCode("ES");

        State s = new State();
        s.setCountry(cy);
        s.setIsoCode("Z");
        s.setName("Zaragoza");

        City c = new City();
        c.setState(s);
        c.setGmt(1);
        c.setName("Zaragoza");
        c.setIata("ZAZ");

        route.setDestinyCity(c);
        route.setOriginCity(c);
        route.setConnections(new ArrayList<>());
        return route;
    }

    public static Route createRichRoute(
            String originIata,
            String destinyIata,
            String originTime,
            String destinyTime,
            List<Connection> connections
    ) {

        Route route = new Route();

        route.setDepartureTime(LocalTime.parse(originTime));
        route.setArrivalTime(LocalTime.parse(destinyTime));

        City oc = createCity(originIata);
        City dc = createCity(destinyIata);


        route.setOriginCity(oc);
        route.setDestinyCity(dc);

        Duration d = Duration.between(route.getDepartureTime(), route.getArrivalTime());

        if (d.isNegative()) {
            d = d.plusHours(24);
        }

        route.setConnections(connections);
        route.setDurationTime(
                LocalTime.parse(TimeUtils.parse(d))
        );
        return route;
    }

    public static Connection createConnection(Route fullRoute, Route connectionRoute) {
        Connection cn = new Connection();
        cn.setConnectionRoute(connectionRoute);
        cn.setFullRoute(fullRoute);
        return cn;
    }

    public static City createCity(String iata) {
        City ct = new City();
        ct.setIata(iata);
        ct.setName(iata + "City");
        ct.setGmt(0);

        Country c = new Country();
        c.setIsoCode("ES");
        c.setName("Spain");

        State s1 = new State();
        s1.setIsoCode(iata.substring(2));
        s1.setCountry(c);

        ct.setState(s1);
        return ct;
    }

    public static Route createRichRoute(
            String originIata,
            String destinyIata,
            String originTime,
            String destinyTime) {

        return createRichRoute(originIata, destinyIata, originTime, destinyTime, null);
    }

    public static Route routeTime1Connections3() {

        Route r = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAD",
                "02:30:10",
                "03:38:28"
        );

        Connection cn1 = RouteTestFactory.createConnection(r, RouteTestFactory.createMonoRoute());
        Connection cn2 = RouteTestFactory.createConnection(r, RouteTestFactory.createMonoRoute());
        Connection cn3 = RouteTestFactory.createConnection(r, RouteTestFactory.createMonoRoute());

        r.setConnections(Arrays.asList(cn1, cn2, cn3));

        return r;
    }

    public static Route routeTime2Connections0() {

        Route r = RouteTestFactory.createRichRoute(
                "ZAZ",
                "BCN",
                "02:30:10",
                "03:58:28"
        );

        return r;
    }

    public static Route routeTime3Connections1() {

        Route r = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAL",
                "22:30:10",
                "23:59:58"
        );

        Connection cn1 = RouteTestFactory.createConnection(r, RouteTestFactory.createMonoRoute());

        r.setConnections(Collections.singletonList(cn1));

        return r;
    }

    public static Route routeTime4Connections0() {

        Route r = RouteTestFactory.createRichRoute(
                "ZAZ",
                "BCN",
                "02:30:10",
                "04:31:28"
        );

        return r;
    }

    public static Route routeTime5Connections2() {

        Route r = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAD",
                "02:30:10",
                "07:31:28"
        );

        Connection cn1 = RouteTestFactory.createConnection(r, RouteTestFactory.createMonoRoute());
        Connection cn2 = RouteTestFactory.createConnection(r, RouteTestFactory.createMonoRoute());

        r.setConnections(Arrays.asList(cn1, cn2));

        return r;

    }

    public static Route routeTime6Connections0() {

        Route r = RouteTestFactory.createRichRoute(
                "ZAZ",
                "VAL",
                "02:30:10",
                "09:31:28"
        );

        return r;
    }
}
