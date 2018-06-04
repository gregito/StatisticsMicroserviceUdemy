package com.example.microservices.StatisticsMicroservice.api.endpoint;

import com.example.microservices.StatisticsMicroservice.utilities.JsonResponseBody;
import org.springframework.http.ResponseEntity;

public interface StatisticsEndpoint {

    String test();

    ResponseEntity<JsonResponseBody> getStatistics(String jwt, String email);

}
