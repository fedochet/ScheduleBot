package org.anstreth.schedulebot.schedulerbotcommandshandler.handlers;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.commands.UserCommand;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.NoScheduleForWeekResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.WeekResponse;
import org.anstreth.schedulebot.schedulerrepository.SchedulerRepository;
import org.anstreth.utils.TimeSupplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeekScheduleRequestHandlerTest {

    @InjectMocks
    private WeekScheduleRequestHandler weekHandler;

    @Mock
    private SchedulerRepository repository;

    @Mock
    private TimeSupplier timeSupplier;

    @Test
    public void handlerShouldTakeWeekFromRepoAndReturnWeekResponse() {
        int groupId = 1;
        WeekSchedule weekFromRepo = new WeekSchedule();
        weekFromRepo.setDays(Collections.singletonList(new Day()));
        Calendar date = mock(Calendar.class);
        when(timeSupplier.now()).thenReturn(date);
        given(repository.getScheduleForGroupForWeek(groupId, date)).willReturn(weekFromRepo);

        WeekResponse response = (WeekResponse) weekHandler.handle(new ScheduleRequest(groupId, UserCommand.WEEK));

        assertThat(response.getWeekSchedule(), is(weekFromRepo));
    }

    @Test
    public void ifWeekDaysAreNullThen_noScheduleForWeek_isReturned() {
        int groupId = 1;
        WeekSchedule weekFromRepo = new WeekSchedule();
        weekFromRepo.setDays(null);
        Calendar date = mock(Calendar.class);
        when(timeSupplier.now()).thenReturn(date);
        when(repository.getScheduleForGroupForWeek(groupId, date)).thenReturn(weekFromRepo);

        ScheduleResponse response = weekHandler.handle(new ScheduleRequest(groupId, UserCommand.WEEK));

        assertThat(response, is(instanceOf(NoScheduleForWeekResponse.class)));
    }

    @Test
    public void ifWeekDaysAreEmptyThen_noSheduleForWeek_isReturned() {
        int groupId = 1;
        WeekSchedule weekFromRepo = new WeekSchedule();
        weekFromRepo.setDays(Collections.emptyList());
        Calendar date = mock(Calendar.class);
        when(timeSupplier.now()).thenReturn(date);
        when(repository.getScheduleForGroupForWeek(groupId, date)).thenReturn(weekFromRepo);

        ScheduleResponse response = weekHandler.handle(new ScheduleRequest(groupId, UserCommand.WEEK));

        assertThat(response, is(instanceOf(NoScheduleForWeekResponse.class)));
    }
}
