package com.adidas.travel.service;

import com.adidas.travel.RouteTestFactory;
import com.adidas.travel.domain.Connection;
import com.adidas.travel.domain.Itinerary;
import com.adidas.travel.domain.Route;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class ItineraryServiceImplTest {

    private OriginDestinyService originDestinyService;

    private ItineraryService itineraryService;

    private List<Route> listRoute;

    @BeforeClass
    public void setUp() {
        originDestinyService = mock(OriginDestinyService.class);
        itineraryService = new ItineraryServiceImpl(originDestinyService);
        listRoute = messedUpOrderRouteList();
    }

    @Test
    public void shouldReturnSortedByTimeTest() {

        when(originDestinyService.getRoutesByOrigin("ZAZ")).thenReturn(listRoute);

        List<Itinerary> st = itineraryService.listSortedByTime("ZAZ");

        assertEquals(st.get(0).getDestiny().getLocalTime(), LocalTime.parse("03:38:28"));
        assertEquals(st.get(1).getDestiny().getLocalTime(), LocalTime.parse("03:58:28"));
        assertEquals(st.get(2).getDestiny().getLocalTime(), LocalTime.parse("23:59:58"));
        assertEquals(st.get(3).getDestiny().getLocalTime(), LocalTime.parse("04:31:28"));
        assertEquals(st.get(4).getDestiny().getLocalTime(), LocalTime.parse("07:31:28"));
        assertEquals(st.get(5).getDestiny().getLocalTime(), LocalTime.parse("09:31:28"));
    }

    @Test
    public void shouldReturnSortedByConnectionsTest() {

        when(originDestinyService.getRoutesByOrigin("ZAZ")).thenReturn(listRoute);

        List<Itinerary> st = itineraryService.listSortedByConnections("ZAZ");

        assertNull(st.get(0).getConnections());
        assertNull(st.get(1).getConnections());
        assertNull(st.get(2).getConnections());
        assertEquals(st.get(3).getConnections().size(), 1);
        assertEquals(st.get(4).getConnections().size(), 2);
        assertEquals(st.get(5).getConnections().size(), 3);
    }

    private List<Route> messedUpOrderRouteList() {

        List<Route> routes = new ArrayList<>();

        Route fr1 = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAD",
                "02:30:10",
                "07:31:28",
                null
        );

        Connection cn11 = RouteTestFactory.createConnection(fr1, RouteTestFactory.createMonoRoute());
        Connection cn12 = RouteTestFactory.createConnection(fr1, RouteTestFactory.createMonoRoute());
        Connection cn13 = RouteTestFactory.createConnection(fr1, RouteTestFactory.createMonoRoute());

        fr1.setConnections(Arrays.asList(cn11, cn12, cn13));

        routes.add(fr1);

        Route fr2 = RouteTestFactory.createRichRoute(
                "ZAZ",
                "BCN",
                "02:30:10",
                "04:31:28",
                null
        );

        routes.add(fr2);

        Route fr3 = RouteTestFactory.createRichRoute(
                "ZAZ",
                "VAL",
                "02:30:10",
                "09:31:28",
                null
        );

        Connection cn31 = RouteTestFactory.createConnection(fr3, RouteTestFactory.createMonoRoute());

        fr3.setConnections(Arrays.asList(cn31));

        routes.add(fr3);

        Route fr4 = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAD",
                "02:30:10",
                "03:38:28",
                null
        );

        routes.add(fr4);

        Route fr5 = RouteTestFactory.createRichRoute(
                "ZAZ",
                "MAL",
                "22:30:10",
                "23:59:58",
                null
        );

        Connection cn51 = RouteTestFactory.createConnection(fr5, RouteTestFactory.createMonoRoute());
        Connection cn52 = RouteTestFactory.createConnection(fr5, RouteTestFactory.createMonoRoute());

        fr5.setConnections(Arrays.asList(cn51, cn52));

        routes.add(fr5);

        Route fr6 = RouteTestFactory.createRichRoute(
                "ZAZ",
                "BCN",
                "02:30:10",
                "03:58:28",
                null
        );

        routes.add(fr6);

        return routes;
    }

}