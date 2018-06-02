package com.example.microservices.StatisticsMicroservice.service;

import com.example.microservices.StatisticsMicroservice.dao.StatisticsDao;
import com.example.microservices.StatisticsMicroservice.entities.Statistics;
import com.example.microservices.StatisticsMicroservice.utilities.ToDoDataCollector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class StatisticsServiceTest {

    private static final String TEST_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb21lLmVtYWlsQHByb3ZpZGVyLmNvbSIsImV4cCI6M"
            + "TUyNzgwMzE5NCwibmFtZSI6IlN0ZXZlIEJyb25uZXIifQ.WFBwfLDW-ETOmMUhqILlB743PjLKfXxOkM0SZWOKQNg";

    private static final String TEST_EMAIL = "some@email.com";

    // 1928.06.17 - "The Lindy" :)
    private static final long TEST_DATE_IN_FAR_PAST = -1310875200L;

    private static final long HOURS = 24;

    private static final long MINUTES = 60;

    private static final long MILLISECONDS = 1000;

    @InjectMocks
    private StatisticsService underTest;

    @Mock
    private StatisticsDao statisticsDao;

    @Mock
    private ToDoDataCollector toDoDataCollector;

    private Date dateInFuture;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dateInFuture = new Date();
        dateInFuture.setTime(dateInFuture.getTime() + HOURS * MINUTES * MILLISECONDS);
    }

    @Test
    public void testGetStatisticsWhenNoToDosAndThereIsNoStatisticsThenEmptyListShouldReturn() {
        List<Statistics> expected = new ArrayList<>(0);
        when(toDoDataCollector.getNewDataFromToDoService(TEST_JWT)).thenReturn(Collections.emptyList());
        when(statisticsDao.getLastTenStatistics(TEST_EMAIL)).thenReturn(expected);

        List<Statistics> result = underTest.getStatistics(TEST_JWT, TEST_EMAIL);

        assertEquals(expected, result);
        verify(toDoDataCollector, times(1)).getNewDataFromToDoService(TEST_JWT);
        verify(statisticsDao, times(1)).getLastTenStatistics(TEST_EMAIL);
        verify(statisticsDao, times(0)).save(any(Statistics.class));
    }

    @Test
    public void testGetStatisticsWhenHasStatisticsAndItsOlderThanADayThenNewRecordShouldPlaced() {
        Date date = new Date();
        date.setTime(TEST_DATE_IN_FAR_PAST);
        Statistics statistic = new Statistics(null, "some value", date, TEST_EMAIL);
        List<Statistics> statistics = new ArrayList<>(1);
        statistics.add(statistic);
        Statistics expectedAfterSave = new Statistics();
        when(statisticsDao.getLastTenStatistics(TEST_EMAIL)).thenReturn(statistics);
        when(statisticsDao.save(any(Statistics.class))).thenReturn(expectedAfterSave);

        List<Statistics> result = underTest.getStatistics(TEST_JWT, TEST_EMAIL);

        assertEquals(2, result.size());
        assertTrue(result.contains(expectedAfterSave));
        verify(statisticsDao, times(1)).save(any(Statistics.class));
        verify(statisticsDao, times(1)).getLastTenStatistics(TEST_EMAIL);
        verify(toDoDataCollector, times(1)).getNewDataFromToDoService(TEST_JWT);
    }

    @Test
    public void testGetStatisticsWhenHasStatisticsAndItsNoOlderThanADayThenNewRecordShouldPlaced() {
        Statistics statistic = new Statistics(null, "some value", dateInFuture, TEST_EMAIL);
        List<Statistics> statistics = new ArrayList<>(1);
        statistics.add(statistic);
        Statistics expectedAfterSave = new Statistics();
        when(statisticsDao.getLastTenStatistics(TEST_EMAIL)).thenReturn(statistics);
        when(statisticsDao.save(any(Statistics.class))).thenReturn(expectedAfterSave);

        List<Statistics> result = underTest.getStatistics(TEST_JWT, TEST_EMAIL);

        assertEquals(1, result.size());
        assertFalse(result.contains(expectedAfterSave));
        verify(statisticsDao, times(0)).save(any(Statistics.class));
        verify(statisticsDao, times(1)).getLastTenStatistics(TEST_EMAIL);
        verify(toDoDataCollector, times(1)).getNewDataFromToDoService(TEST_JWT);
    }

}