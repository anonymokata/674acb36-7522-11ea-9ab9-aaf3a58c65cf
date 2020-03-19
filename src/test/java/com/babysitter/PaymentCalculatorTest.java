package com.babysitter;

import org.junit.Assert;
import org.junit.Test;

public class PaymentCalculatorTest {

    @Test
    public void whenCalculateIsCalledTotalPayIsZero() {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        Assert.assertEquals(0, paymentCalculator.calculate("18:00", "19:00", "A"), 0.01);
    }
}
