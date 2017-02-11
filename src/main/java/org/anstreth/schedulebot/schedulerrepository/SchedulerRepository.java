package org.anstreth.schedulebot.schedulerrepository;

import org.anstreth.ruzapi.Day;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public interface SchedulerRepository {
    Day getScheduleForDate(Calendar date);
}
