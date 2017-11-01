package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.WeekSchedule;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SimpleSchedulerFormatterTest {
    @InjectMocks
    private SimpleSchedulerFormatter schedulerFormatter;

    @Mock
    private DayFormatter dayFormatter;

    @Test
    public void eachDayIn_WeekResponse_isFormattedWith_dayFormatter() {
        String formattedDayOne = "day one";
        String formattedDayTwo = "day two";
        Day mockDayTwo = mock(Day.class);
        Day mockDayOne = mock(Day.class);
        WeekResponse weekResponse = new WeekResponse(getWeekSchedule(mockDayTwo, mockDayOne));
        doReturn(formattedDayOne).when(dayFormatter).formatDay(mockDayOne);
        doReturn(formattedDayTwo).when(dayFormatter).formatDay(mockDayTwo);

        assertThat(schedulerFormatter.format(weekResponse),
                contains(formattedDayOne, formattedDayTwo));
    }

    @Test
    public void DayResponse_isFormattedWithDayFormatter() throws Exception {
        Day mockDay = mock(Day.class);
        String formattedDay = "formattedDay";
        DayResponse dayResponse = new DayResponse(mockDay);
        doReturn(formattedDay).when(dayFormatter).formatDay(mockDay);

        assertThat(schedulerFormatter.format(dayResponse), contains(formattedDay));
    }

    @Test
    public void NoScheduleForDay_responseIsFormattedCorrectly() throws Exception {
        List<String> messages = schedulerFormatter.format(mock(NoScheduleForDayResponse.class));

        assertThat(messages, contains("There are no lessons!"));
    }

    @Test
    public void NoScheduleForWeek_responseIsFormattedCorrectly() {
        List<String> messages = schedulerFormatter.format(mock(NoScheduleForWeekResponse.class));

        assertThat(messages, contains("There are no schedule for this week!"));
    }

    @Test
    public void SimpleStringMessage_isJustReturned() throws Exception {
        assertThat(schedulerFormatter.format(new SimpleStringResponse("message")), contains("message"));
    }

    private WeekSchedule getWeekSchedule(Day mockDayTwo, Day mockDayOne) {
        WeekSchedule weekSchedule = new WeekSchedule();
        weekSchedule.setDays(Arrays.asList(mockDayOne, mockDayTwo));
        return weekSchedule;
    }

}
