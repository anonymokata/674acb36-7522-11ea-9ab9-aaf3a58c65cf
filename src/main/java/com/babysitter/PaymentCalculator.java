package com.babysitter;

import org.apache.commons.lang3.StringUtils;

public class PaymentCalculator {

    public Integer calculate(String startTime, String endTime, String family) {
        validateInputs(startTime, endTime, family);
        return 0;
    }

    private void validateInputs(String startTime, String endTime, String family) {
        checkIfInputsAreBlankOrNot(startTime, endTime, family);
    }

    private void checkIfInputsAreBlankOrNot(String startTime, String endTime, String family) {
        if(StringUtils.isBlank(startTime))
            System.out.println("Start time cannot be blank!");
        if(StringUtils.isBlank(endTime))
            System.out.println("End time cannot be blank!");
        if(StringUtils.isBlank(family))
            System.out.println("Family cannot be blank!");
    }
}
