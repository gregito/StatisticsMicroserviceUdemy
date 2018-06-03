package com.example.microservices.StatisticsMicroservice.api.endpoint;

import com.example.microservices.StatisticsMicroservice.utilities.JsonResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface StatisticsEndpoint {

    @RequestMapping("/test")
    String test();

    @RequestMapping("/getStatistics")
    ResponseEntity<JsonResponseBody> getStatistics(@RequestParam(value = "jwt") String jwt, @RequestParam(value = "email") String email);

}
