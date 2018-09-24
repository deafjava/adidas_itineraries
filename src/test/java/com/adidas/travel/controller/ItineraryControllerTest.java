package com.adidas.travel.controller;

import com.adidas.travel.RouteListTestFactory;
import com.adidas.travel.domain.Itinerary;
import com.adidas.travel.service.ItineraryService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@WebMvcTest(ItineraryController.class)
public class ItineraryControllerTest {
    private MockMvc mockMvc;

    private ItineraryService itineraryService;

    @BeforeClass
    public void setUp() {
        itineraryService = mock(ItineraryService.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new ItineraryController(itineraryService))
                .setControllerAdvice(new ErrorHubController())
                .build();
    }

    @Test
    public void shouldReturnOrderedListOfRoutesByTimeTest() throws Exception {

        String iata = "ZAZ";

        List<Itinerary> itineraries = RouteListTestFactory
                .byTimeOrderedRouteList()
                .stream()
                .map(Itinerary::of)
                .collect(Collectors.toList());

        when(itineraryService.listSortedByTime(iata)).thenReturn(itineraries);

        MvcResult result = mockMvc.perform(get("/itinerary/bytime/" + iata))
                .andExpect(status().isOk())
                .andReturn();

        verify(itineraryService).listSortedByTime(eq(iata));

        JSONParser jsonParser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        JSONArray jsonArray = (JSONArray) jsonParser.parse(result.getResponse().getContentAsString());

        assertEquals(jsonArray.size(), 6L);

        Object o = jsonArray.get(0);
        JSONObject jo = (JSONObject) o;
        JSONObject jsonObject = (JSONObject) jo.get("destiny");
        assertEquals(jsonObject.getAsString("localTime"), "03:38:28");

        o = jsonArray.get(5);
        jo = (JSONObject) o;
        jsonObject = (JSONObject) jo.get("destiny");
        assertEquals(jsonObject.getAsString("localTime"), "09:31:28");
    }

    @Test
    public void shouldReturnOrderedListOfRoutesByConnectionsTest() throws Exception {

        String iata = "ZAZ";

        List<Itinerary> itineraries = RouteListTestFactory
                .byConnectionsOrderedRouteList()
                .stream()
                .map(Itinerary::of)
                .collect(Collectors.toList());

        when(itineraryService.listSortedByConnections(iata)).thenReturn(itineraries);

        MvcResult result = mockMvc.perform(get("/itinerary/byconnections/" + iata))
                .andExpect(status().isOk())
                .andReturn();

        verify(itineraryService).listSortedByConnections(eq(iata));

        JSONParser jsonParser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        JSONArray jsonArray = (JSONArray) jsonParser.parse(result.getResponse().getContentAsString());

        assertEquals(jsonArray.size(), 6L);

        Object o = jsonArray.get(0);
        JSONObject jo = (JSONObject) o;
        JSONObject jsonObject = (JSONObject) jo.get("destiny");
        assertEquals(jsonObject.getAsString("localTime"), "03:58:28");

        o = jsonArray.get(5);
        jo = (JSONObject) o;
        jsonObject = (JSONObject) jo.get("destiny");
        assertEquals(jsonObject.getAsString("localTime"), "03:38:28");
    }

    @Test
    public void everythingWentWrongTest() throws Exception {

        String iata = "NO";

        given(itineraryService.listSortedByConnections(iata)).willAnswer(invocation -> {
            throw new Exception();
        });

        MvcResult result = mockMvc.perform(get("/itinerary/byconnections/" + iata))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Oops! Something wrong happened!"));

    }

    @Test
    public void pageNotFoundTest() throws Exception {

        String iata = "NO";

        mockMvc.perform(get("/" + iata))
                .andExpect(status().isNotFound())
                .andReturn();

    }
}