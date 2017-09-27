package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForWeekResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.WeekResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.anstreth.utils.TimeSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class WeekScheduleRequestHandler implements SchedulerRequestHandler {

    private final SchedulerRepository repository;
    private final TimeSupplier timeSupplier;

    @Autowired
    public WeekScheduleRequestHandler(SchedulerRepository repository, TimeSupplier timeSupplier) {
        this.repository = repository;
        this.timeSupplier = timeSupplier;
    }

    @Override
    public ScheduleResponse handle(ScheduleRequest request) {
        Calendar now = timeSupplier.now();
        WeekSchedule week = repository.getScheduleForGroupForWeek(request.getGroupId(), now);
        if (weekHasNoDays(week)) {
            return new NoScheduleForWeekResponse(now);
        }

        return new WeekResponse(week);
    }

    private boolean weekHasNoDays(WeekSchedule week) {
        return week.getDays() == null || week.getDays().isEmpty();
    }
}
