package com.babysitter;

import com.babysitter.enums.FamilyEnum;
import com.babysitter.exception.InvalidTimeFormatException;
import com.babysitter.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.babysitter.constants.Constants.END_TIME;
import static com.babysitter.constants.Constants.START_TIME_HOUR;

public class PaymentCalculator {

    public Integer calculate(String startTime, String endTime, String family) throws InvalidTimeFormatException {
        if (validateTimesAndFamily(startTime, endTime, family)) {
            LocalDateTime startDateTime = DateUtil.convertStringIntoLocalDateTime(startTime);
            LocalDateTime endDateTime = DateUtil.convertStringIntoLocalDateTime(endTime);
            if (areTimesValid(startDateTime, endDateTime)) {
                Integer totalPay = calculateTotalPay(startDateTime, endDateTime, family);
            }
        }
        return 0;
    }

    private Integer calculateTotalPay(LocalDateTime startDateTime, LocalDateTime endDateTime, String family) {
        Integer totalPay = 0;
        return totalPay;
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

        if (isStartTimeNotWithinWorkableHours(startDateTime) || isEndTimeNotWithinWorkableHours(startDateTime, endDateTime)) {
            areTimesNotWithinWorkableHours = Boolean.TRUE;
        }
        return areTimesNotWithinWorkableHours;
    }

    private Boolean isStartTimeNotWithinWorkableHours(LocalDateTime startDateTime) {
        Boolean isStartTimeNotWithinWorkableHours = Boolean.FALSE;

        if (startDateTime.getHour() < START_TIME_HOUR) {
            System.out.println("The start time should not be earlier than 5:00PM");
            isStartTimeNotWithinWorkableHours = Boolean.TRUE;
        }
        return isStartTimeNotWithinWorkableHours;
    }

    private Boolean isEndTimeNotWithinWorkableHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Boolean isEndTimeNotWithinWorkableHours = Boolean.FALSE;

        if (!(startDateTime.toLocalDate().equals(endDateTime.toLocalDate()))) {
            // There is already a check in the function isEndTimeBeforeStartTime to see if the end time is before the start time. That should ensure that the end time is on or after the start time.
            // This check is for checking if the end time is before 4am for the next day

            // Create a new LocalDateTime which will be used to compare with the endDateTime
            // The date of the LocalDateTime will be the startDateTime with one more day
            LocalDate localDateForNextDay = startDateTime.toLocalDate().plusDays(1);
            // The time of the LocalDateTime will be 4AM
            LocalTime localTimeForNextDay = LocalTime.parse(END_TIME);
            LocalDateTime dateTimeForNextDay = LocalDateTime.of(localDateForNextDay, localTimeForNextDay);
            if (endDateTime.isAfter(dateTimeForNextDay)) {
                System.out.println("The end time cannot be past 4AM and the end date either has to be on the same or the next day of the start date!");
                isEndTimeNotWithinWorkableHours = Boolean.TRUE;
            }

        }
        return isEndTimeNotWithinWorkableHours;
    }


}
