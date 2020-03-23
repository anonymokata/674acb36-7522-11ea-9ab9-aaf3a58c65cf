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
    public void whenGetDifferenceInHoursIsCalledAndTheTotalDifferenceInHoursIsFullThenTheTotalNumberOfHoursCalculatedIsFullAndIsReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 18:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 19:00");
        Integer result = timeAndPayUtil.getDifferenceInHours(startDateTime, endDateTime);
        assertEquals(1, result, 0.01);
    }

    @Test
    public void whenGetDifferenceInHoursIsCalledAndTheTotalDifferenceInHoursIsFractionalThenTheTotalNumberOfHoursCalculatedIsFullAndIsReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 18:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 20:01");
        Integer result = timeAndPayUtil.getDifferenceInHours(startDateTime, endDateTime);
        assertEquals(2, result, 0.01);
    }

    @Test
    public void whenCalculatePaymentBasedOnHoursIsCalledThenPaymentCalculatedIsReturned() {
        Integer result = timeAndPayUtil.calculatePaymentBasedOnHours(2, 15);
        assertEquals(30, result, 0.01);
    }

    @Test
    public void whenGetTotalPayForSingleTimeLimitIsCalledWhereStartAndEndDateTimesAreBeforeTheLimitDateTimeThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 17:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        LocalDateTime limitDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        Integer payBeforeLimit = 10;
        Integer payAfterLimit = 15;

        Integer result = timeAndPayUtil.getTotalPayForSingleTimeLimit(startDateTime, endDateTime, limitDateTime, payBeforeLimit, payAfterLimit);
        assertEquals(60, result, 0.01);
    }

}
