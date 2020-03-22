package com.babysitter.utils;

import com.babysitter.exception.InvalidTimeFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DateUtilTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test(expected = InvalidTimeFormatException.class)
    public void whenConvertStringIntoLocalDateTimeIsCalledWithAnInvalidFormattedDateThenAnErrorMessageIsPrintedAndAnInvalidTimeFormatExceptionIsThrown() throws InvalidTimeFormatException {
        DateUtil.convertStringIntoLocalDateTime("2020-13-21 18:00");
        Assert.assertEquals("The start time or end time is in an invalid format! Please use the format: \"yyyy-MM-dd HH:mm\" and please ensure that the times are correct", outputStream.toString().trim());
    }

    @Test()
    public void whenConvertStringIntoLocalDateTimeIsCalledWithAValidFormattedDateThenNoErrorMessageIsPrinted() throws InvalidTimeFormatException {
        DateUtil.convertStringIntoLocalDateTime("2020-03-21 18:00");
        Assert.assertEquals("", outputStream.toString().trim());
    }

}