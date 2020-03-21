package com.babysitter;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class PaymentCalculator {

    public Integer calculate(String startTime, String endTime, String family) {
        LocalDateTime startDateTime;
        if (checkIfInputsAreBlankOrNot(startTime, endTime, family)) {
            startDateTime = convertStringIntoLocalDateTime(startTime);
        }
        return 0;
    }

    private Boolean checkIfInputsAreBlankOrNot(String startTime, String endTime, String family) {
        Boolean areInputsValid = Boolean.TRUE;
        if(StringUtils.isBlank(startTime)) {
            System.out.println("Start time cannot be blank!");
            areInputsValid = Boolean.FALSE;
        }
        if(StringUtils.isBlank(endTime)) {
            System.out.println("End time cannot be blank!");
            areInputsValid = Boolean.FALSE;
        }
        if(StringUtils.isBlank(family)) {
            System.out.println("Family cannot be blank!");
            areInputsValid = Boolean.FALSE;
        }
        return areInputsValid;
    }

    private LocalDateTime convertStringIntoLocalDateTime(String date) {
        LocalDateTime dateTime = null;
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm");

        try {
            dateTime = LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("The start time or end time is in an invalid format! Please use the format: \"yyyy-MM-dd HH:mm\" and please ensure that the times are correct");
        }
        return dateTime;
    }
}
