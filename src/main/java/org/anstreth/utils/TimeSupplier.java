package org.anstreth.utils;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

@Component
public class TimeSupplier {
    private final TimeZone TARGET_TIMEZONE = TimeZone.getTimeZone("Russia/Moscow");

    public Calendar now() {
        return Calendar.getInstance(TARGET_TIMEZONE);
    }

}
