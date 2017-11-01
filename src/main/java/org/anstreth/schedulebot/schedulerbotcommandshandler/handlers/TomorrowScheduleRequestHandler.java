package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;

import java.util.Calendar;

public class TomorrowScheduleRequestHandler extends OneDayScheduleRequestHandler {
    TomorrowScheduleRequestHandler(SchedulerRepository schedulerRepository) {
        super(schedulerRepository);
    }

    public ScheduleResponse handle(ScheduleRequest request) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 1);
        return getScheduleResponseForGroupForDate(request.getGroupId(), today);
    }
}
