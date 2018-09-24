package com.adidas.travel.client;

import com.adidas.travel.domain.Route;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

@Component
public class RouteApiClientImpl implements RouteApiClient {

    private static final String PATH_ROUTE = "route/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    @Value("${auth.user}")
    private String user;

    @Value("${auth.password}")
    private String password;

    @Override
    public List<Route> getRoutes(String iata) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("routes-service", false);

        String serviceBaseUrl = instanceInfo.getHomePageUrl();

        String uri = serviceBaseUrl + PATH_ROUTE + iata;

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
