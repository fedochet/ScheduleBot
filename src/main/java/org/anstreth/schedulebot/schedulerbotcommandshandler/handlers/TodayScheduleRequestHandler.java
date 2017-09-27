package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.anstreth.utils.TimeSupplier;

public class TodayScheduleRequestHandler extends OneDayScheduleRequestHandler {
    private final TimeSupplier timeSupplier;

    TodayScheduleRequestHandler(SchedulerRepository schedulerRepository, TimeSupplier timeSupplier) {
        super(schedulerRepository);
        this.timeSupplier = timeSupplier;
    }

    public ScheduleResponse handle(ScheduleRequest request) {
        return getScheduleResponseForGroupForDate(request.getGroupId(), timeSupplier.now());
    }
}
