package com.babysitter;

import com.babysitter.exception.InvalidTimeFormatException;
import com.babysitter.utils.DateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class PaymentCalculatorTest {

    private PaymentCalculator paymentCalculator;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        paymentCalculator = new PaymentCalculator();
        System.setOut(new PrintStream(outputStream));
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
        Assert.assertEquals("The Start Time or End Time are not within the Allowed Workable Hours! The Allowed Workable Hours are from 5:00PM to 4:00 AM inclusive.", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereStartTimeIsWithinWorkableHoursThenThereIsNoErrorMessagePrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 17:00", "A");
        Assert.assertEquals("", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereEndTimeIsAfterFourAMThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:01", "A");
        Assert.assertEquals("The Start Time or End Time are not within the Allowed Workable Hours! The Allowed Workable Hours are from 5:00PM to 4:00 AM inclusive.", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereEndTimeIsBeforeFourAMButAFewDaysAfterTheStartDateThenAnErrorMessageIsPrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-23 03:59", "A");
        Assert.assertEquals("The Start Time or End Time are not within the Allowed Workable Hours! The Allowed Workable Hours are from 5:00PM to 4:00 AM inclusive.", outputStream.toString().trim());
    }

    @Test
    public void whenCalculateIsCalledWhereStartAndEndTimesAreWithinWorkableHoursThenThereIsNoErrorMessagePrinted() throws InvalidTimeFormatException {
        paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:00", "A");
        Assert.assertEquals("", outputStream.toString().trim());
    }

    // Family A

    @Test
    public void whenCalculateIsCalledForFamilyAWhereStartAndEndDateTimesAreEqualThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 17:00", "A");
        assertEquals(0, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyAWhereStartAndEndDateTimesAreBeforeTheLimitDateTimeThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 23:00", "A");
        assertEquals(90, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyAWhereLimitDateTimeIsBetweenStartAndEndDateTimesThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 00:00", "A");
        assertEquals(110, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyAWhereStartDateTimeEndDateTimeAndLimitDateTimeAreAllEqualThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 23:00", "2020-03-21 23:00", "A");
        assertEquals(0, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyAWhereStartAndEndDateTimeAreAfterTheLimitDateTimeThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-22 00:00", "2020-03-22 02:00", "A");
        assertEquals(40, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyAWhereStartTimeIsEqualToLimitDateTimeAndEndDateTimeIsAfterLimitDateTimeThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 23:00", "2020-03-22 04:00", "A");
        assertEquals(100, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyAWhereStartTimeIsFivePMAndEndTimeIsFourAMThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 04:00", "A");
        assertEquals(190, result, 0.01);
    }

    //    Family B

    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereStartAndEndTimeAreEqualThenTotalPayIsCalculatedAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 18:00", "2020-03-21 18:00", "B");
        assertEquals(0, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereEndTimeIsBeforeTheFirstLimitThenTotalPayIsCalculatedAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 21:00", "B");
        assertEquals(48, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereEndTimeIsEqualToTheFirstLimitThenTotalPayIsCalculatedAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 22:00", "B");
        assertEquals(60, result, 0.01);
    }


    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereEndTimeIsBetweenTheLimitsThenTotalPayIsCalculatedAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 23:00", "B");
        assertEquals(68, result, 0.01);
    }


    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereEndTimeIsEqualToTheSecondLimitThenTotalPayIsCalculatedAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 00:00", "B");
        assertEquals(76, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereStartTimeIsBeforeTheFirstLimitAndEndTimeIsAfterTheSecondLimitThenTotalPayCalculatedIsReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 01:00", "B");
        assertEquals(92, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereStartTimeIsEqualToTheFirstLimitAndEndTimeIsAfterTheSecondLimitThenTotalPayCalculatedIsReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 22:00", "2020-03-22 01:00", "B");
        assertEquals(32, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereStartTimeIsBetweenTheLimitsAndEndTimeIsAfterTheSecondLimitThenTotalPayCalculatedIsReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 23:00", "2020-03-22 01:00", "B");
        assertEquals(24, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereStartTimeAndEndTimeAreEqualToTheSecondLimitThenTotalPayIsZero() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 22:00", "2020-03-21 22:00", "B");
        assertEquals(0, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyBIsCalledWhereStartTimeAndEndTimeAreAfterTheSecondLimitThenTotalPayCalculatedIsReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-22 01:00", "2020-03-22 04:00", "B");
        assertEquals(48, result, 0.01);
    }

    // Family C
    @Test
    public void whenCalculateIsCalledForFamilyCWhereStartAndEndDateTimesAreEqualThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 17:00", "C");
        assertEquals(0, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyCWhereStartAndEndDateTimesAreBeforeTheLimitDateTimeThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-21 20:00", "C");
        assertEquals(63, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyCWhereLimitDateTimeIsBetweenStartAndEndDateTimesThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 17:00", "2020-03-22 00:00", "C");
        assertEquals(129, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyCWhereStartDateTimeEndDateTimeAndLimitDateTimeAreAllEqualThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 21:00", "2020-03-21 21:00", "C");
        assertEquals(0, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyCWhereStartAndEndDateTimeAreAfterTheLimitDateTimeThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-22 00:00", "2020-03-22 02:00", "C");
        assertEquals(30, result, 0.01);
    }

    @Test
    public void whenCalculateIsCalledForFamilyCWhereStartTimeIsEqualToLimitDateTimeAndEndDateTimeIsAfterLimitDateTimeThenTotalPayIsCalculatedCorrectlyAndReturned() throws InvalidTimeFormatException {
        Integer result = paymentCalculator.calculate("2020-03-21 21:00", "2020-03-22 04:00", "C");
        assertEquals(105, result, 0.01);
    }


}