package org.anstreth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

@Component
@PropertySource("classpath:bot.properties")
public class TimeSupplier {
    private final TimeZone TARGET_TIMEZONE;

    @Autowired
    TimeSupplier(@Value("${bot.target-timezone}") String timezoneName) {
        TARGET_TIMEZONE = TimeZone.getTimeZone(timezoneName);
    }

    public Calendar now() {
        return Calendar.getInstance(TARGET_TIMEZONE);
    }

}
