package com.babysitter;

import com.babysitter.enums.FamilyEnum;
import com.babysitter.exception.InvalidTimeFormatException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaymentCalculator {

    public Integer calculate(String startTime, String endTime, String family) throws InvalidTimeFormatException {
        if (validateTimesAndFamily(startTime, endTime, family)) {
            LocalDateTime startDateTime = convertStringIntoLocalDateTime(startTime);
            LocalDateTime endDateTime = convertStringIntoLocalDateTime(endTime);
            if (areTimesValid(startDateTime, endDateTime)) {
                return 0;
            }
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

    private Boolean areTimesValid(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Boolean areTimesValid = Boolean.TRUE;
        if (isEndTimeBeforeStartTime(startDateTime, endDateTime) || areTimesNotWithinWorkableHours(startDateTime, endDateTime)) {
            areTimesValid = Boolean.FALSE;
        }
        return areTimesValid;
    }

    private Boolean isEndTimeBeforeStartTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Boolean isEndTimeBeforeStartTime = Boolean.FALSE;
        if (endDateTime.isBefore(startDateTime)) {
            System.out.println("End time cannot be before the start time!");
            isEndTimeBeforeStartTime = Boolean.TRUE;
        }
        return isEndTimeBeforeStartTime;
    }

    private Boolean areTimesNotWithinWorkableHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Boolean areTimesNotWithinWorkableHours = Boolean.FALSE;

        if (isStartTimeNotWithinWorkableHours(startDateTime)) {
            areTimesNotWithinWorkableHours = Boolean.TRUE;
        }
        return areTimesNotWithinWorkableHours;
    }

    private Boolean isStartTimeNotWithinWorkableHours(LocalDateTime startDateTime) {
        Boolean isStartTimeNotWithinWorkableHours = Boolean.FALSE;
        if(startDateTime.getHour() < 17) {
            System.out.println("The start time should not be earlier than 5:00PM");
            isStartTimeNotWithinWorkableHours = Boolean.TRUE;
        }
        return isStartTimeNotWithinWorkableHours;
    }

    private LocalDateTime convertStringIntoLocalDateTime(String date) throws InvalidTimeFormatException {
        LocalDateTime dateTime = null;
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
