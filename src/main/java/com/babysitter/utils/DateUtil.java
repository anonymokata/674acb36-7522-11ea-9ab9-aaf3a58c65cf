package com.babysitter.utils;

import com.babysitter.exception.InvalidTimeFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    public static LocalDateTime convertStringIntoLocalDateTime(String date) throws InvalidTimeFormatException {
        LocalDateTime dateTime;
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm");

        try {
            dateTime = LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("The start time or end time is in an invalid format! Please use the format: \"yyyy-MM-dd HH:mm\" and please ensure that the times are correct");
            throw new InvalidTimeFormatException("The start or end time format is not correct and could not be parsed correctly");
        }
        return dateTime;
    }

}
