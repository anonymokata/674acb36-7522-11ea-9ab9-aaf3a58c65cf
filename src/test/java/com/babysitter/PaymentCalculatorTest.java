package com.babysitter;

import com.babysitter.exception.InvalidTimeFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PaymentCalculatorTest {

    private PaymentCalculator paymentCalculator;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        paymentCalculator = new PaymentCalculator();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void whenCalculateIsCalledThenTotalPayIsZero() throws InvalidTimeFormatException {
        Assert.assertEquals(0, paymentCalculator.calculate("2020-03-21 18:00", "2020-03-21 19:00", "A"), 0.01);
    }

    @Test
    public void whenCalculateIsCalledWithABlankStartTimeThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("", "2020-03-21 19:00", "A");
        Assert.assertEquals("Start time cannot be blank!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWithABlankEndTimeThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 18:00", "", "A");
        Assert.assertEquals("End time cannot be blank!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWithABlankFamilyThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 18:00", "2020-03-21 19:00", "");
        Assert.assertEquals("Family cannot be blank!", outputStream.toString().trim());
    }

    @Test(expected = InvalidTimeFormatException.class)
    public void whenCalculateIsCalledWithAnInvalidStartTimeThenAnErrorMessageIsPrintedAndAnInvalidTimeFormatExceptionIsThrown() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 25:00", "2020-03-21 19:00", "A");
        Assert.assertEquals("The start time or end time is in an invalid format! Please use the format: \"yyyy-MM-dd HH:mm\" and please ensure that the times are correct", outputStream.toString().trim());

    }

    @Test
    public void whenCalculateIsCalledAndTheInputsAreValidThenTheStringTimeIsConvertedCorrectly() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:00", "A");
        Assert.assertEquals("", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledAndFamilyIsInALowerCaseFormatThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:00", "a");
        Assert.assertEquals("The family is not valid!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledAndFamilyIsNotValidThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
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
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 04:00", "A");
        Assert.assertEquals("End time cannot be before the start time!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereStartTimeAndEndTimeAreEqualThenThereIsNoErrorMessagePrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 17:00", "A");
        Assert.assertEquals("", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereStartTimeIsNotWithinWorkableHoursThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 16:59", "2020-03-21 17:00", "A");
        Assert.assertEquals("The start time should not be earlier than 5:00PM", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereStartTimeIsWithinWorkableHoursThenThereIsNoErrorMessagePrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 17:00", "A");
        Assert.assertEquals("", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereEndTimeIsAfterFourAMThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:01", "A");
        Assert.assertEquals("The end time cannot be past 4AM and the end date either has to be on the same or the next day of the start date!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereEndTimeIsBeforeFourAMButAFewDaysAfterTheStartDateThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-23 03:59", "A");
        Assert.assertEquals("The end time cannot be past 4AM and the end date either has to be on the same or the next day of the start date!", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereStartAndEndTimesAreWithinWorkableHoursThenThereIsNoErrorMessagePrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:00", "A");
        Assert.assertEquals("", outputStream.toString().trim());
    }


}