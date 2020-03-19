package com.babysitter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PaymentCalculatorTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void whenCalculateIsCalledThenTotalPayIsZero() {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        Assert.assertEquals(0, paymentCalculator.calculate("18:00", "19:00", "A"), 0.01);
    }

    @Test
    public void whenCalculateIsCalledWithABlankStartTimeThenAnErrorMessageIsPrinted() {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("", "19:00", "A");
        Assert.assertEquals("Start time cannot be blank!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWithABlankEndTimeThenAnErrorMessageIsPrinted() {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("18:00", "", "A");
        Assert.assertEquals("End time cannot be blank!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWithABlankFamilyThenAnErrorMessageIsPrinted() {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("18:00", "19:00", "");
        Assert.assertEquals("Family cannot be blank!", outputStream.toString().trim());
    }

}
