package com.babysitter;

import com.babysitter.calculator.PaymentCalculator;
import com.babysitter.exception.InvalidTimeFormatException;

public class BabysitterPaymentCalculator {

    public static void main(String args[]) throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        // Start Date Time and End Date Time Format is yyyy-MM-dd HH:mm
        int result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 17:00", "A");
        System.out.println("The Total Payment is: $" + result);
    }

}
