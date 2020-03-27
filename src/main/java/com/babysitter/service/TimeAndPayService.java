package com.babysitter.service;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAndPayService {

    public int getTotalPayForSingleTimeLimit(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime limitDateTime, Integer payPerHourBeforeTimeLimit, Integer payPerHourAfterTimeLimit) {
        int totalPay = 0;
        if (!startDateTime.equals(endDateTime)) {
            // When startDateTime and EndDateTime are on or before limitDateTime
            if (endDateTime.isEqual(limitDateTime) || endDateTime.isBefore(limitDateTime)) {
                totalPay = calcuatePaymentBasedOnTimes(startDateTime, endDateTime, payPerHourBeforeTimeLimit);
            }
            // When limitDateTime is between startDateTime and EndDateTime
            if ((startDateTime.isBefore(limitDateTime) || startDateTime.isEqual(limitDateTime)) && endDateTime.isAfter(limitDateTime)) {
                int payBeforeLimit = calcuatePaymentBasedOnTimes(startDateTime, limitDateTime, payPerHourBeforeTimeLimit);
                int payAfterLimit = calcuatePaymentBasedOnTimes(limitDateTime, endDateTime, payPerHourAfterTimeLimit);
                totalPay = payBeforeLimit + payAfterLimit;
            }
            // When startDateTime and EndDateTime are on or after limitDateTime
            if (startDateTime.isAfter(limitDateTime) && (endDateTime.isAfter(limitDateTime))) {
                totalPay = calcuatePaymentBasedOnTimes(startDateTime, endDateTime, payPerHourAfterTimeLimit);
            }
        }
        return totalPay;
    }

    public int getTotalPayForDoubleTimeLimit(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime firstLimit, LocalDateTime secondLimit, Integer payPerHourBeforeFirstLimit, Integer payPerHourBetweenLimits, Integer payPerHourAfterSecondLimit) {
        int totalPay = 0;
        if (endDateTime.isEqual(secondLimit) || endDateTime.isBefore(secondLimit)) {
            totalPay = getTotalPayForSingleTimeLimit(startDateTime, endDateTime, firstLimit, payPerHourBeforeFirstLimit, payPerHourBetweenLimits);
        } else {
            if (startDateTime.isBefore(firstLimit)) {
                int payUntilFirstLimit = calcuatePaymentBasedOnTimes(startDateTime, firstLimit, payPerHourBeforeFirstLimit);
                int payFromFirstLimitUntilEndDateTime = getTotalPayForSingleTimeLimit(firstLimit, endDateTime, secondLimit, payPerHourBetweenLimits, payPerHourAfterSecondLimit);
                totalPay = payUntilFirstLimit + payFromFirstLimitUntilEndDateTime;
            } else {
                totalPay = getTotalPayForSingleTimeLimit(startDateTime, endDateTime, secondLimit, payPerHourBetweenLimits, payPerHourAfterSecondLimit);
            }
        }
        return totalPay;
    }

    public int calcuatePaymentBasedOnTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int payPerHour) {
        int totalHours = getDifferenceInHours(startDateTime, endDateTime);
        return calculatePaymentBasedOnHours(totalHours, payPerHour);
    }


    public int getDifferenceInHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (int) Duration.between(startDateTime, endDateTime).toHours();
    }

    public int calculatePaymentBasedOnHours(int hours, int payPerHour) {
        return payPerHour * hours;
    }
}
