package org.anstreth.schedulebot.schedulerformatter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.DayResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForDayResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForWeekResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.WeekResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SimpleSchedulerFormatter implements SchedulerFormatter {

    private final DayFormatter dateFormatter;

    @Autowired
    public SimpleSchedulerFormatter(DayFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public String formatDay(Day scheduleForToday) {
        return dateFormatter.formatDay(scheduleForToday);
    }

    @Override
    public List<String> format(DayResponse dayResponse) {
        return Collections.singletonList(formatDay(dayResponse.getDay()));
    }

    @Override
    public List<String> format(WeekResponse weekResponse) {
        return weekResponse.getWeekSchedule().getDays().stream()
                .map(this::formatDay)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> format(NoScheduleForDayResponse response) {
        return Collections.singletonList("There are no lessons!");
    }

    @Override
    public List<String> format(NoScheduleForWeekResponse response) {
        return Collections.singletonList("There are no schedule for this week!");
    }

}
