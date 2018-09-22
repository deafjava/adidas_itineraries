package com.adidas.travel.client.service;

import com.adidas.travel.client.domain.Route;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

@Service
public class OriginDestinyServiceImpl implements OriginDestinyService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${route.api.url}")
    private String urlApi;

    @Value("${auth.user}")
    private String user;

    @Value("${auth.password}")
    private String password;

    @Override
    public List<Route> getRoutesByOrigin(String iata) {

        String uri = urlApi + iata;

        ResponseEntity<List<Route>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(user, password)),
                new ParameterizedTypeReference<List<Route>>() {
                });

        return response.getBody();

    }

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);

                set("Authorization", authHeader);
            }
        };
    }
}
