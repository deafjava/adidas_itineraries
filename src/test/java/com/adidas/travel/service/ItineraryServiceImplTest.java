package com.adidas.travel.service;

import com.adidas.travel.domain.Itinerary;
import com.adidas.travel.domain.Route;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.List;

import static com.adidas.travel.RouteListTestFactory.messedUpOrderRouteList;
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


}