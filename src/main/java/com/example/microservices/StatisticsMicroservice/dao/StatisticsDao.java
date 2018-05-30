package com.example.microservices.StatisticsMicroservice.dao;

import com.example.microservices.StatisticsMicroservice.entities.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticsDao extends JpaRepository<Statistics, Integer> {

    @Query(value = "SELECT * FROM latest_statistics WHERE EMAIL=:email ORDER BY DATE DESC LIMIT 10", nativeQuery = true)
    List<Statistics> getLastTenStatistics(@Param("email") String email);


}
