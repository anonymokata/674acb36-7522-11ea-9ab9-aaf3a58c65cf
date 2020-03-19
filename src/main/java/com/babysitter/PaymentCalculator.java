package com.babysitter;

import org.apache.commons.lang3.StringUtils;

public class PaymentCalculator {

    public Integer calculate(String startTime, String endTime, String family) {
        if(StringUtils.isBlank(startTime))
            System.out.println("Start time cannot be blank!");
        return 0;
    }
}
