package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.anstreth.utils.TimeSupplier;

import java.util.Calendar;

public class TommorowScheduleRequestHandler extends OneDayScheduleRequestHandler {
    private final TimeSupplier timeSupplier;

    TommorowScheduleRequestHandler(SchedulerRepository schedulerRepository, TimeSupplier timeSupplier) {
        super(schedulerRepository);
        this.timeSupplier = timeSupplier;
    }

    public ScheduleResponse handle(ScheduleRequest request) {
        Calendar today = timeSupplier.now();
        today.add(Calendar.DATE, 1);
        return getScheduleResponseForGroupForDate(request.getGroupId(), today);
    }
}
