package com.babysitter.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAndPayUtil {

    public Integer getTotalPayForSingleTimeLimit(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime limitDateTime, Integer payBeforeTimeLimit, Integer payAfterTimeLimit) {
        Integer totalPay = 0;
        // When startDateTime and EndDateTime are on or before limitDateTime
        if (endDateTime.isEqual(limitDateTime) || endDateTime.isBefore(limitDateTime)) {
            Integer totalHours = getDifferenceInHours(startDateTime, endDateTime);
            totalPay = calculatePaymentBasedOnHours(totalHours, payBeforeTimeLimit);
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
