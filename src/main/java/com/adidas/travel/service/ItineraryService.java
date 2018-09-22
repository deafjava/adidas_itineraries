package com.adidas.travel.service;

import com.adidas.travel.domain.Itinerary;

import java.util.List;

public interface ItineraryService {

    List<Itinerary> listSortedByTime(String originIata);

}
