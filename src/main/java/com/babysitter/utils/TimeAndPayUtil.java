package com.babysitter.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAndPayUtil {

    public Integer getTotalPayForSingleTimeLimit(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime limitDateTime, Integer payPerHourBeforeTimeLimit, Integer payPerHourAfterTimeLimit) {
        Integer totalPay = 0;
        if(!startDateTime.equals(endDateTime)) {
            // When startDateTime and EndDateTime are on or before limitDateTime
            if (endDateTime.isEqual(limitDateTime) || endDateTime.isBefore(limitDateTime)) {
                Integer totalHours = getDifferenceInHours(startDateTime, endDateTime);
                totalPay = calculatePaymentBasedOnHours(totalHours, payPerHourBeforeTimeLimit);
            }
            // When limitDateTime is between startDateTime and EndDateTime
            if ((startDateTime.isBefore(limitDateTime) || startDateTime.isEqual(limitDateTime)) && endDateTime.isAfter(limitDateTime)) {
                Integer hoursBeforeLimit = getDifferenceInHours(startDateTime, limitDateTime);
                Integer hoursAfterLimit = getDifferenceInHours(limitDateTime, endDateTime);
                Integer totalPayBeforeLimit = calculatePaymentBasedOnHours(hoursBeforeLimit, payPerHourBeforeTimeLimit);
                Integer totalPayAfterLimit = calculatePaymentBasedOnHours(hoursAfterLimit, payPerHourAfterTimeLimit);
                totalPay = totalPayBeforeLimit + totalPayAfterLimit;
            }
            // When startDateTime and EndDateTime are on or after limitDateTime
            if (startDateTime.isAfter(limitDateTime) && (endDateTime.isAfter(limitDateTime))) {
                Integer totalHours = getDifferenceInHours(startDateTime, endDateTime);
                totalPay = calculatePaymentBasedOnHours(totalHours, payPerHourAfterTimeLimit);
            }
        }
        return totalPay;
    }

    public Integer getTotalPayForDoubleTimeLimit(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime firstLimit, LocalDateTime secondLimit, Integer payPerHourBeforeFirstLimit, Integer payPerHourBetweenLimits, Integer payPerHourAfterSecondLimit) {
        Integer totalPay = 0;
        if(endDateTime.isEqual(firstLimit) || endDateTime.isBefore(firstLimit)) {
            totalPay = getTotalPayForSingleTimeLimit(startDateTime, endDateTime, firstLimit, payPerHourBeforeFirstLimit, payPerHourBetweenLimits);
        }
        return totalPay;
    }








        public Integer getDifferenceInHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (int) Duration.between(startDateTime, endDateTime).toHours();
    }

    public Integer calculatePaymentBasedOnHours(Integer hours, Integer payPerHour) {
        return payPerHour * hours;
    }
}
