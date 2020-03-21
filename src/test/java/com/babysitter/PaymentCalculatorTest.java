package com.babysitter;

import com.babysitter.exception.InvalidTimeFormatException;
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
    public void whenCalculateIsCalledThenTotalPayIsZero() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        Assert.assertEquals(0, paymentCalculator.calculate("2020-03-21 18:00", "2020-03-21 19:00", "A"), 0.01);
    }

    @Test
    public void whenCalculateIsCalledWithABlankStartTimeThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("", "2020-03-21 19:00", "A");
        Assert.assertEquals("Start time cannot be blank!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWithABlankEndTimeThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("2020-03-21 18:00", "", "A");
        Assert.assertEquals("End time cannot be blank!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWithABlankFamilyThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("2020-03-21 18:00", "2020-03-21 19:00", "");
        Assert.assertEquals("Family cannot be blank!", outputStream.toString().trim());
    }

    @Test(expected = InvalidTimeFormatException.class)
    public void whenCalculateIsCalledWithAnInvalidStartTimeThenAnErrorMessageIsPrintedAndAnInvalidTestFormatExceptionIsThrown() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("2020-03-21 25:00", "2020-03-21 19:00", "A");
        Assert.assertEquals("The start time or end time is in an invalid format! Please use the format: \"yyyy-MM-dd HH:mm\" and please ensure that the times are correct", outputStream.toString().trim());

    }

    @Test
    public void whenCalculateIsCalledAndTheInputsAreValidThenTheStringTimeIsConvertedCorrectly() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:00", "A");
        Assert.assertEquals("", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledAndFamilyIsInALowerCaseFormatThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:00", "a");
        Assert.assertEquals("The family is not valid!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledAndFamilyIsNotValidThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:00", "E");
        Assert.assertEquals("The family is not valid!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledAndFamilyIsValidThenThereIsNoErrorMessagePrinted() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:00", "A");
        Assert.assertEquals("", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledAndEndTimeIsBeforeStartTimeThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 04:00", "A");
        Assert.assertEquals("End time cannot be before the start time!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereStartTimeAndEndTimeAreEqualThenThereIsNoErrorMessagePrinted() throws InvalidTimeFormatException {
        PaymentCalculator paymentCalculator = new PaymentCalculator();
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 17:00", "A");
        Assert.assertEquals("", outputStream.toString().trim());
    }
}