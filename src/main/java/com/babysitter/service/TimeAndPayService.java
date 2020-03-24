package com.babysitter.service;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAndPayService {

    public Integer getTotalPayForSingleTimeLimit(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime limitDateTime, Integer payPerHourBeforeTimeLimit, Integer payPerHourAfterTimeLimit) {
        Integer totalPay = 0;
        if (!startDateTime.equals(endDateTime)) {
            // When startDateTime and EndDateTime are on or before limitDateTime
            if (endDateTime.isEqual(limitDateTime) || endDateTime.isBefore(limitDateTime)) {
                totalPay = calcuatePaymentBasedOnTimes(startDateTime, endDateTime, payPerHourBeforeTimeLimit);
            }
            // When limitDateTime is between startDateTime and EndDateTime
            if ((startDateTime.isBefore(limitDateTime) || startDateTime.isEqual(limitDateTime)) && endDateTime.isAfter(limitDateTime)) {
                Integer payBeforeLimit = calcuatePaymentBasedOnTimes(startDateTime, limitDateTime, payPerHourBeforeTimeLimit);
                Integer payAfterLimit = calcuatePaymentBasedOnTimes(limitDateTime, endDateTime, payPerHourAfterTimeLimit);
                totalPay = payBeforeLimit + payAfterLimit;
            }
            // When startDateTime and EndDateTime are on or after limitDateTime
            if (startDateTime.isAfter(limitDateTime) && (endDateTime.isAfter(limitDateTime))) {
                totalPay = calcuatePaymentBasedOnTimes(startDateTime, endDateTime, payPerHourAfterTimeLimit);
            }
        }
        return totalPay;
    }

    public Integer getTotalPayForDoubleTimeLimit(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime firstLimit, LocalDateTime secondLimit, Integer payPerHourBeforeFirstLimit, Integer payPerHourBetweenLimits, Integer payPerHourAfterSecondLimit) {
        Integer totalPay = 0;
        if (endDateTime.isEqual(secondLimit) || endDateTime.isBefore(secondLimit)) {
            totalPay = getTotalPayForSingleTimeLimit(startDateTime, endDateTime, firstLimit, payPerHourBeforeFirstLimit, payPerHourBetweenLimits);
        } else {
            if (startDateTime.isBefore(firstLimit)) {
                Integer payUntilFirstLimit = calcuatePaymentBasedOnTimes(startDateTime, firstLimit, payPerHourBeforeFirstLimit);
                Integer payFromFirstLimitUntilEndDateTime = calcuatePaymentBasedOnTimes(firstLimit, endDateTime, payPerHourBetweenLimits);
                totalPay = payUntilFirstLimit + payFromFirstLimitUntilEndDateTime;
            } else {
                totalPay = getTotalPayForSingleTimeLimit(startDateTime, endDateTime, secondLimit, payPerHourBetweenLimits, payPerHourAfterSecondLimit);
            }
        }
        return totalPay;
    }

    public Integer calcuatePaymentBasedOnTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer payPerHour) {
        Integer totalHours = getDifferenceInHours(startDateTime, endDateTime);
        return calculatePaymentBasedOnHours(totalHours, payPerHour);
    }


    public Integer getDifferenceInHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (int) Duration.between(startDateTime, endDateTime).toHours();
    }

    public Integer calculatePaymentBasedOnHours(Integer hours, Integer payPerHour) {
        return payPerHour * hours;
    }
}
