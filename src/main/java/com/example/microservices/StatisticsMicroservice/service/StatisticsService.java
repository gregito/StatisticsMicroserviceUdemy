package com.example.microservices.StatisticsMicroservice.service;

import com.example.microservices.StatisticsMicroservice.dao.StatisticsDao;
import com.example.microservices.StatisticsMicroservice.entities.Statistics;
import com.example.microservices.StatisticsMicroservice.utilities.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;

    public List<Statistics> getStatistics(String jwt, String email) {
        List<LinkedHashMap> todos = getNewDataFromToDoService(jwt);
        String statisticsDescr = "No statistics available";
        if (!todos.isEmpty()) {
            int lowPriorityToDoQuantity = 0;
            int highPriorityToDoQuantity = 0;
            for (LinkedHashMap todo : todos) {
                String priority = (String) todo.get("priority");
                if ("low".equals(priority)) {
                    lowPriorityToDoQuantity++;
                }
                if ("high".equals(priority)) {
                    lowPriorityToDoQuantity++;
                }
                statisticsDescr = String.format(
                        "You have <b>%d low priority</b> ToDo and <b>%d high priority</b> ToDo",
                        lowPriorityToDoQuantity, highPriorityToDoQuantity);
            }
        }
        List<Statistics> statistics = statisticsDao.getLastTenStatistics(email);
        if (!statistics.isEmpty()) {
            Date now = new Date();
            long diffInMilisec = now.getTime() - statistics.get(0).getDate().getTime();
            long diffInDays = diffInMilisec / (24 * 60 * 1000);
            if (diffInDays > 1) {
                statistics.add(statisticsDao.save(new Statistics(null, statisticsDescr, new Date(), email)));
            }
        }
        return statistics;
    }

    @SuppressWarnings("unchecked")
    private List<LinkedHashMap> getNewDataFromToDoService(String jwt) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("jwt", jwt);
        HttpEntity<?> request = new HttpEntity<>(String.class, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonResponseBody> response = restTemplate.exchange(
                String.format("http://%s:%s/showToDos", System.getenv("TODO_SERVICE_IP"), System.getenv("TODO_SERVICE_PORT")),
                HttpMethod.POST,
                request,
                JsonResponseBody.class);
        return (List) response.getBody();
    }

}