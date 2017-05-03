package org.anstreth.schedulebot.schedulerbotcommandshandler.response;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeekResponseTest {

    @Mock
    private SchedulerFormatter formatter;

    @Mock
    private MessageSender sender;

    @Test
    public void responseShouldTakeAllDaysFromWeek_ThenFormatIt_ThenSendIt() {
        Day dayOne = mock(Day.class);
        Day dayTwo = mock(Day.class);
        String formattedDayOne = "day one";
        String formattedDayTwo = "day two";
        WeekSchedule weekSchedule = new WeekSchedule();
        weekSchedule.setDays(Arrays.asList(dayOne, dayTwo));
        WeekResponse weekResponse = new WeekResponse(weekSchedule);
        given(formatter.formatDay(dayOne)).willReturn(formattedDayOne);
        given(formatter.formatDay(dayTwo)).willReturn(formattedDayTwo);

        weekResponse.formatAndSend(formatter, sender);

        then(sender).should().sendMessage(formattedDayOne);
        then(sender).should().sendMessage(formattedDayTwo);
    }
}