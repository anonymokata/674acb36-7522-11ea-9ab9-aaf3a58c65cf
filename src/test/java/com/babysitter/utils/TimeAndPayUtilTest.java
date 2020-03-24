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
    public void whenGetTotalPayForSingleTimeLimitIsCalledWhereStartAndEndDateTimesAreEqualThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 17:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 17:00");
        LocalDateTime limitDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        Integer payBeforeLimit = 10;
        Integer payAfterLimit = 15;

        Integer result = timeAndPayUtil.getTotalPayForSingleTimeLimit(startDateTime, endDateTime, limitDateTime, payBeforeLimit, payAfterLimit);
        assertEquals(0, result, 0.01);
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

    @Test
    public void whenGetTotalPayForSingleTimeLimitIsCalledWhereLimitDateTimeIsBetweenStartAndEndDateTimesThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 17:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-22 00:00");
        LocalDateTime limitDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        Integer payBeforeLimit = 10;
        Integer payAfterLimit = 15;

        Integer result = timeAndPayUtil.getTotalPayForSingleTimeLimit(startDateTime, endDateTime, limitDateTime, payBeforeLimit, payAfterLimit);
        assertEquals(75, result, 0.01);
    }

    @Test
    public void whenGetTotalPayForSingleTimeLimitIsCalledWhereStartDateTimeEndDateTimeAndLimitDateTimeAreAllEqualThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        LocalDateTime limitDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        Integer payBeforeLimit = 10;
        Integer payAfterLimit = 15;

        Integer result = timeAndPayUtil.getTotalPayForSingleTimeLimit(startDateTime, endDateTime, limitDateTime, payBeforeLimit, payAfterLimit);
        assertEquals(0, result, 0.01);
    }

    @Test
    public void whenGetTotalPayForSingleTimeLimitIsCalledWhereStartAndEndDateTimeAreGreaterThanLimitDateTimeThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-22 00:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-22 02:00");
        LocalDateTime limitDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        Integer payBeforeLimit = 10;
        Integer payAfterLimit = 15;

        Integer result = timeAndPayUtil.getTotalPayForSingleTimeLimit(startDateTime, endDateTime, limitDateTime, payBeforeLimit, payAfterLimit);
        assertEquals(30, result, 0.01);
    }

    @Test
    public void whenGetTotalPayForSingleTimeLimitIsCalledWhereStartTimeEqualToLimitDateTimeAndEndDateTimeIsAfterLimitDateTimeThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-22 04:00");
        LocalDateTime limitDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        Integer payBeforeLimit = 10;
        Integer payAfterLimit = 15;

        Integer result = timeAndPayUtil.getTotalPayForSingleTimeLimit(startDateTime, endDateTime, limitDateTime, payBeforeLimit, payAfterLimit);
        assertEquals(75, result, 0.01);
    }

    @Test
    public void whenGetTotalPayForSingleTimeLimitIsCalledWhereStartTimeIsFivePMAndEndTimeIsFourAMThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 17:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-22 04:00");
        LocalDateTime limitDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        Integer payBeforeLimit = 10;
        Integer payAfterLimit = 15;

        Integer result = timeAndPayUtil.getTotalPayForSingleTimeLimit(startDateTime, endDateTime, limitDateTime, payBeforeLimit, payAfterLimit);
        assertEquals(135, result, 0.01);
    }

    @Test
    public void whenGetTotalPayForDoubleTimeLimitIsCalledWhereStartAndEndTimeAreEqualThenTotalPayIsCalculatedAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 18:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 18:00");
        LocalDateTime firstLimit = DateUtil.convertStringIntoLocalDateTime("2020-03-21 22:00");
        LocalDateTime secondLimit = DateUtil.convertStringIntoLocalDateTime("2020-03-22 00:00");
        Integer payPerHourBeforeFirstLimit = 15;
        Integer payPerHourBetweenLimits = 12;
        Integer payPerHourAfterSecondLimit = 21;

        Integer result = timeAndPayUtil.getTotalPayForDoubleTimeLimit(startDateTime, endDateTime, firstLimit, secondLimit, payPerHourBeforeFirstLimit, payPerHourBetweenLimits, payPerHourAfterSecondLimit);
        assertEquals(0, result, 0.01);
    }

    @Test
    public void whenGetTotalPayForDoubleTimeLimitIsCalledWhereEndTimeIsLessThanTheFirstLimitThenTotalPayIsCalculatedAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 17:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 21:00");
        LocalDateTime firstLimit = DateUtil.convertStringIntoLocalDateTime("2020-03-21 22:00");
        LocalDateTime secondLimit = DateUtil.convertStringIntoLocalDateTime("2020-03-22 00:00");
        Integer payPerHourBeforeFirstLimit = 15;
        Integer payPerHourBetweenLimits = 12;
        Integer payPerHourAfterSecondLimit = 21;

        Integer result = timeAndPayUtil.getTotalPayForDoubleTimeLimit(startDateTime, endDateTime, firstLimit, secondLimit, payPerHourBeforeFirstLimit, payPerHourBetweenLimits, payPerHourAfterSecondLimit);
        assertEquals(60, result, 0.01);
    }

    @Test
    public void whenGetTotalPayForDoubleTimeLimitIsCalledWhereEndTimeIsEqualToTheFirstLimitThenTotalPayIsCalculatedAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 17:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 22:00");
        LocalDateTime firstLimit = DateUtil.convertStringIntoLocalDateTime("2020-03-21 22:00");
        LocalDateTime secondLimit = DateUtil.convertStringIntoLocalDateTime("2020-03-22 00:00");
        Integer payPerHourBeforeFirstLimit = 15;
        Integer payPerHourBetweenLimits = 12;
        Integer payPerHourAfterSecondLimit = 21;

        Integer result = timeAndPayUtil.getTotalPayForDoubleTimeLimit(startDateTime, endDateTime, firstLimit, secondLimit, payPerHourBeforeFirstLimit, payPerHourBetweenLimits, payPerHourAfterSecondLimit);
        assertEquals(75, result, 0.01);
    }

    @Test
    public void whenGetTotalPayForDoubleTimeLimitIsCalledWhereEndTimeIsBetweenTheLimitsThenTotalPayIsCalculatedAndReturned() throws InvalidTimeFormatException {
        LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 17:00");
        LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime("2020-03-21 23:00");
        LocalDateTime firstLimit = DateUtil.convertStringIntoLocalDateTime("2020-03-21 22:00");
        LocalDateTime secondLimit = DateUtil.convertStringIntoLocalDateTime("2020-03-22 00:00");
        Integer payPerHourBeforeFirstLimit = 15;
        Integer payPerHourBetweenLimits = 12;
        Integer payPerHourAfterSecondLimit = 21;

        Integer result = timeAndPayUtil.getTotalPayForDoubleTimeLimit(startDateTime, endDateTime, firstLimit, secondLimit, payPerHourBeforeFirstLimit, payPerHourBetweenLimits, payPerHourAfterSecondLimit);
        assertEquals(87, result, 0.01);
    }




}
