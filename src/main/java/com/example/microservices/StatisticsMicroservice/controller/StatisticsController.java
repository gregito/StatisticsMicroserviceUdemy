package com.example.microservices.StatisticsMicroservice.controller;

import com.example.microservices.StatisticsMicroservice.api.endpoint.StatisticsEndpoint;
import com.example.microservices.StatisticsMicroservice.service.StatisticsService;
import com.example.microservices.StatisticsMicroservice.utilities.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController implements StatisticsEndpoint {

    @Autowired
    private StatisticsService statisticsService;

    @Override
    public String test() {
        return "Statistics microservice is available";
    }

    @Override
    public ResponseEntity<JsonResponseBody> getStatistics(String jwt, String email) {
        return ResponseEntity.ok(new JsonResponseBody(HttpStatus.OK.value(), statisticsService.getStatistics(jwt, email)));
    }

}
