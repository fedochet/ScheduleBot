package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.schedulebot.commands.UserCommand;
import org.anstreth.schedulebot.exceptions.NoScheduleForDay;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.DayResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForDayResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.SimpleStringResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TomorrowScheduleRequestHandlerTest {
    @InjectMocks
    private TomorrowScheduleRequestHandler handler;
    @Mock
    private SchedulerRepository schedulerRepository;

    @Test
    public void tomorrowHandlerGetsCurrentDayFromRepoAndReturnsDayResponse() {
        int groupId = 1;
        Day day = new Day();
        when(schedulerRepository.getScheduleForGroupForDay(eq(groupId), any(Calendar.class))).thenReturn(day);

        ScheduleResponse response = handler.handle(new ScheduleRequest(groupId, UserCommand.TOMORROW));

        assertThat(response, is(instanceOf(DayResponse.class)));
    }

    @Test
    public void ifRepositoryThrowsNoScheduleException_Then_NoScheduleResponseIsReturned() {
        int groupId = 1;
        when(schedulerRepository.getScheduleForGroupForDay(eq(groupId), any(Calendar.class))).thenThrow(new NoScheduleForDay());

        ScheduleResponse response = handler.handle(new ScheduleRequest(groupId, UserCommand.TOMORROW));

        assertThat(response, is(instanceOf(NoScheduleForDayResponse.class)));
    }

    @Test
    public void ifRepositoryThrowsException_Then_SimpleStringResponseIsReturned() {
        int groupId = 1;
        when(schedulerRepository.getScheduleForGroupForDay(eq(groupId), any(Calendar.class))).thenThrow(new RuntimeException());

        ScheduleResponse response = handler.handle(new ScheduleRequest(groupId, UserCommand.TOMORROW));

        assertThat(response, is(instanceOf(SimpleStringResponse.class)));
    }
}
