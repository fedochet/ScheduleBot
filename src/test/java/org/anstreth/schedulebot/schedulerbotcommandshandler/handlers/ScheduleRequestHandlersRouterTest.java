package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.anstreth.schedulebot.commands.UserCommand.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleRequestHandlersRouterTest {

    @InjectMocks
    private ScheduleRequestHandlersRouter router;

    @Test
    public void returnsTodayHandlerIfCommandIsToday() throws Exception {

        assertThat(router.getHandlerForCommand(TODAY), isA(TodayScheduleRequestHandler.class));
    }

    @Test
    public void returnsTomorrowIfCommandIs_TOMORROW() throws Exception {
        assertThat(router.getHandlerForCommand(TOMORROW), isA(TomorrowScheduleRequestHandler.class));
    }

    @Test
    public void returnWeekHandlerIfCommandIs_WEEK() throws Exception {
        assertThat(router.getHandlerForCommand(WEEK), isA(WeekScheduleRequestHandler.class));
    }

    @Test
    public void returnsUnknownCommandHandlerIfCommandIs_UNKNOWN() throws Exception {
        assertThat(router.getHandlerForCommand(UNKNOWN), isA(UnrecognizedCommandHandler.class));
    }

    private <T> Matcher<SchedulerRequestHandler> isA(Class<T> type) {
        return is(instanceOf(type));
    }
}
