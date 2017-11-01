package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.schedulebot.commands.UserCommand;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRequestHandlersRouter {
    private final SchedulerRepository schedulerRepository;

    ScheduleRequestHandlersRouter(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    public SchedulerRequestHandler getHandlerForCommand(UserCommand command) {
        switch (command) {
            case TODAY:
                return new TodayScheduleRequestHandler(schedulerRepository);
            case TOMORROW:
                return new TomorrowScheduleRequestHandler(schedulerRepository);
            case WEEK:
                return new WeekScheduleRequestHandler(schedulerRepository);
            default:
                return new UnrecognizedCommandHandler();
        }
    }
}
