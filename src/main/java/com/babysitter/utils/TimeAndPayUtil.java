package com.babysitter.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAndPayUtil {

    public Long getHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime).toHours();
    }
}
