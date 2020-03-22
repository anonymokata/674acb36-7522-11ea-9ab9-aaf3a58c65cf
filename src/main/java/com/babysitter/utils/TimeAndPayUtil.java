package com.babysitter.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAndPayUtil {

    public Integer getDifferenceInHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (int) Duration.between(startDateTime, endDateTime).toHours();
    }

    public Integer calculatePaymentBasedOnHours(Integer hours, Integer payPerHour) {
        return payPerHour * hours;
    }
}
