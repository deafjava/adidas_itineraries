package com.adidas.travel.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;

import java.time.LocalTime;

@Data
public class AirportDetail {

    private String iata;

    private String city;

    private String country;

    private LocalTime gmtTime;

    private LocalTime localTime;

    public static AirportDetail of(City city, LocalTime localTime) {
        AirportDetail airportDetail = new AirportDetail();

        airportDetail.setIata(city.getIata());
        airportDetail.setCity(city.getName());
        airportDetail.setCountry(city.getState().getCountry().getIsoCode());
        airportDetail.setGmtTime(localTime.plusHours(city.getGmt()));
        airportDetail.setLocalTime(localTime);

        return airportDetail;
    }

    @JsonGetter("gmtTime")
    public String getRawGmtTime() {
        return gmtTime.toString();
    }

    @JsonGetter("localTime")
    public String getRawLocalTime() {
        return localTime.toString();
    }
}
