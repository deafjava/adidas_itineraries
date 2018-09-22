package com.adidas.travel.client.service;

import com.adidas.travel.client.domain.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OriginDestinyServiceImpl implements OriginDestinyService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${route.api.url}")
    private String urlApi;

    @Override
    public List<Route> getRoutesByOrigin(String iata) {

        String uri = urlApi + iata;

        ResponseEntity<List<Route>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Route>>() {
                });

        return response.getBody();

    }
}
