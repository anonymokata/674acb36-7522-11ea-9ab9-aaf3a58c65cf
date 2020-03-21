package com.babysitter;

import com.babysitter.enums.FamilyEnum;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaymentCalculator {

    public Integer calculate(String startTime, String endTime, String family) {
        LocalDateTime startDateTime;

        if (validateTimesAndFamily(startTime, endTime, family)) {
            startDateTime = convertStringIntoLocalDateTime(startTime);
        }
        return 0;
    }

    private Boolean validateTimesAndFamily(String startTime, String endTime, String family) {
        Boolean areInputsValid = Boolean.TRUE;

        if (!areAllInputsNotBlank(startTime, endTime, family) || !isFamilyValid(family)) {
            areInputsValid = Boolean.FALSE;
        }
        return areInputsValid;
    }

    private Boolean areAllInputsNotBlank(String startTime, String endTime, String family) {
        Boolean areAllInputsNotBlank = Boolean.TRUE;

        if (StringUtils.isBlank(startTime)) {
            System.out.println("Start time cannot be blank!");
            areAllInputsNotBlank = Boolean.FALSE;
        }
        if (StringUtils.isBlank(endTime)) {
            System.out.println("End time cannot be blank!");
            areAllInputsNotBlank = Boolean.FALSE;
        }
        if (StringUtils.isBlank(family)) {
            System.out.println("Family cannot be blank!");
            areAllInputsNotBlank = Boolean.FALSE;
        }
        return areAllInputsNotBlank;
    }

    private Boolean isFamilyValid(String family) {
        Boolean isFamilyValid = Boolean.FALSE;

        for (FamilyEnum familyName : FamilyEnum.values()) {
            if (familyName.toString().equals(family)) {
                isFamilyValid = Boolean.TRUE;
            }
        }
        if (!isFamilyValid) {
            System.out.println("The family is not valid!");
        }
        return isFamilyValid;
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
