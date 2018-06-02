package com.example.microservices.StatisticsMicroservice.utilities;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateProvider {

    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
