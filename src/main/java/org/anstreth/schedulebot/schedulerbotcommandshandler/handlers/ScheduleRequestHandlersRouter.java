package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.commands.UserCommand;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.anstreth.utils.TimeSupplier;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRequestHandlersRouter {
    private final SchedulerRepository schedulerRepository;
    private final TimeSupplier timeSupplier;

    ScheduleRequestHandlersRouter(SchedulerRepository schedulerRepository, TimeSupplier timeSupplier) {
        this.schedulerRepository = schedulerRepository;
        this.timeSupplier = timeSupplier;
    }

    public SchedulerRequestHandler getHandlerForCommand(UserCommand command) {
        switch (command) {
            case TODAY:
                return new TodayScheduleRequestHandler(schedulerRepository, timeSupplier);
            case TOMORROW:
                return new TommorowScheduleRequestHandler(schedulerRepository, timeSupplier);
            case WEEK:
                return new WeekScheduleRequestHandler(schedulerRepository, timeSupplier);
            default:
                return new UnrecognizedCommandHandler();
        }
    }
}
