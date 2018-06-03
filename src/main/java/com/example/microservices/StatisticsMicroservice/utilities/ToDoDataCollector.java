package com.example.microservices.StatisticsMicroservice.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class ToDoDataCollector {

    @Autowired
    private RestTemplateProvider restTemplateProvider;

    @SuppressWarnings("unchecked")
    public List<LinkedHashMap> getNewDataFromToDoService(String jwt) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwt);
        HttpEntity<?> request = new HttpEntity<>(String.class, headers);
        RestTemplate restTemplate = restTemplateProvider.getRestTemplate();
        ResponseEntity<JsonResponseBody> response = restTemplate.exchange(
                String.format("http://%s:%s/showToDos", System.getenv("TODO_SERVICE_IP"), System.getenv("TODO_SERVICE_PORT")),
                HttpMethod.POST,
                request,
                JsonResponseBody.class);
        return (List) response.getBody().getResponse();
    }

}
