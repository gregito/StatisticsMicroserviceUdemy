package com.example.microservices.StatisticsMicroservice.service;

import com.example.microservices.StatisticsMicroservice.dao.StatisticsDao;
import com.example.microservices.StatisticsMicroservice.entities.Statistics;
import com.example.microservices.StatisticsMicroservice.utilities.ToDoDataCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class StatisticsService {

    private static final int INIT_QUANTITY = 0;

    private static final long HOURS = 24;

    private static final long MINUTES = 60;

    private static final long SECONDS = 60;

    private static final long MILLISECONDS = 1000;

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private ToDoDataCollector toDoDataCollector;

    public List<Statistics> getStatistics(String jwt, String email) {
        List<LinkedHashMap> toDos = toDoDataCollector.getNewDataFromToDoService(jwt);
        String statisticsDescription = getDescriptionByPriority(toDos);
        List<Statistics> statistics = statisticsDao.getLastTenStatistics(email);
        if (!statistics.isEmpty()) {
            Date now = new Date();
            long diffInMillisec = now.getTime() - statistics.get(0).getDate().getTime();
            long diffInDays = diffInMillisec / (HOURS * MINUTES * SECONDS * MILLISECONDS);
            if (diffInDays > 1) {
                statistics.add(statisticsDao.save(new Statistics(null, statisticsDescription, new Date(), email)));
            }
        }
        return statistics;
    }

    private String getDescriptionByPriority(List<LinkedHashMap> toDos) {
        String statisticsDescription = "No statistics available";
        if (!toDos.isEmpty()) {
            int lowPriorityToDoQuantity = INIT_QUANTITY;
            int highPriorityToDoQuantity = INIT_QUANTITY;
            for (LinkedHashMap todo : toDos) {
                String priority = (String) todo.get("priority");
                if ("low".equals(priority)) {
                    lowPriorityToDoQuantity++;
                }
                if ("high".equals(priority)) {
                    highPriorityToDoQuantity++;
                }
                statisticsDescription = String.format(
                        "You have <b>%d low priority</b> ToDo and <b>%d high priority</b> ToDo",
                        lowPriorityToDoQuantity, highPriorityToDoQuantity);
            }
        }
        return statisticsDescription;
    }

}
