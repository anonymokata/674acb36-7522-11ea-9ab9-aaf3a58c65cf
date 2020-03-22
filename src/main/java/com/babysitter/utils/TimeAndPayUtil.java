package com.babysitter.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAndPayUtil {

    public Integer getTotalPayForSingleTimeLimit(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime limitDateTime, Integer payBeforeTimeLimit, Integer payAfterTimeLimit) {
        return 0;
    }

    public Integer getDifferenceInHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (int) Duration.between(startDateTime, endDateTime).toHours();
    }

    public Integer calculatePaymentBasedOnHours(Integer hours, Integer payPerHour) {
        return payPerHour * hours;
    }
}
