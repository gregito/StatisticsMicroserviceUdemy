package com.example.microservices.StatisticsMicroservice.controller;

import com.example.microservices.StatisticsMicroservice.api.endpoint.StatisticsEndpoint;
import com.example.microservices.StatisticsMicroservice.service.StatisticsService;
import com.example.microservices.StatisticsMicroservice.utilities.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController implements StatisticsEndpoint {

    @Autowired
    private StatisticsService statisticsService;

    @Override
    @RequestMapping("/test")
    public String test() {
        return "Statistics microservice is available";
    }

    @Override
    @CrossOrigin
    @RequestMapping("/getStatistics")
    public ResponseEntity<JsonResponseBody> getStatistics(@RequestParam(value = "jwt") String jwt, @RequestParam(value = "email") String email) {
        return ResponseEntity.ok(new JsonResponseBody(HttpStatus.OK.value(), statisticsService.getStatistics(jwt, email)));
    }

}
