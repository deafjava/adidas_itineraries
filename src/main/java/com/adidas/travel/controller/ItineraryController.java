package com.adidas.travel.controller;

import com.adidas.travel.domain.Itinerary;
import com.adidas.travel.service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @GetMapping("/bytime/{iata}")
    public List<Itinerary> listByTime(@PathVariable String iata) {
        return itineraryService.listSortedByTime(iata);
    }
}