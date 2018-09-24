package com.adidas.travel;

import com.adidas.travel.domain.*;
import com.adidas.travel.service.utils.TimeUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public static Route createRichRoute(String originIata, String destinyIata, String originTime, String destinyTime, List<Connection> connections) {

        Route route = new Route();

        route.setDepartureTime(LocalTime.parse(originTime));
        route.setArrivalTime(LocalTime.parse(destinyTime));

        City oc = createCity(originIata);
        City dc = createCity(destinyIata);


        route.setOriginCity(oc);
        route.setDestinyCity(dc);

        route.setConnections(connections);
        route.setDurationTime(
                LocalTime.parse(
                        TimeUtils.parse(
                                Duration.between(
                                        route.getDepartureTime(),
                                        route.getArrivalTime()
                                )
                        )
                )
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
}
