package com.adidas.travel.controller;

import com.adidas.travel.domain.Itinerary;
import com.adidas.travel.service.ItineraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/itinerary")
@Api(tags = "Itinerary Service", produces = "application/json", description = "Listing Itinerary giving a city in IATA code")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Page not found"),
        @ApiResponse(code = 422, message = "Invalid iata code!")
})
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @ApiOperation(value = "List itinerary sorted by shortest time of travel in crescent way")
    @GetMapping("/bytime/{iata}")
    public List<Itinerary> listByTime(@PathVariable String iata) {
        return itineraryService.listSortedByTime(iata);
    }

    @ApiOperation(value = "List itinerary sorted by shortest quantity of connections in crescent way")
    @GetMapping("/byconnections/{iata}")
    public List<Itinerary> listByConnections(@PathVariable String iata) {
        return itineraryService.listSortedByConnections(iata);
    }

}
