package com.babysitter.utils;

import com.babysitter.exception.InvalidTimeFormatException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TimeAndPayUtilTest {

    TimeAndPayUtil timeAndPayUtil;

    @Before
    public void setUp() {
        timeAndPayUtil = new TimeAndPayUtil();
    }

    @Test
    public void whenGetHoursIsCalledThenTheTotalNumberOfHoursIsCalculatedAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 18:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 19:00");
        Long result = timeAndPayUtil.getHours(startDateTime, endDateTime);
        assertEquals(1, result, 0.01);
    }

}
