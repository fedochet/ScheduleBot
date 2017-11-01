package org.anstreth.schedulebot.schedulerformatter;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.Lesson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SimpleDayFormatterTest {
    @InjectMocks
    private SimpleDayFormatter dayFormatter;

    @Mock
    private LessonFormatter lessonFormatter;

    @Test
    public void formatterProducesStringStartingFromCertainWordsAndNameOfDayAndDate() throws Exception {
        Date date = new Date();
        Day scheduleForToday = new Day();
        scheduleForToday.setLessons(Collections.emptyList());
        String expectedDayFormat = getExpectedDayFormat(date);
        scheduleForToday.setDate(date);
        String expectedPrefix = String.format("Schedule for %s:\n\n", expectedDayFormat);

        String formattedResult = dayFormatter.formatDay(scheduleForToday);

        assertThat(formattedResult, startsWith(expectedPrefix));
    }

    @Test
    public void everyLessonIsFormattedWithLessonFormatter() throws Exception {
        Lesson lesson = mock(Lesson.class);
        Day dayWithLessons = new Day();
        dayWithLessons.setDate(new Date());
        dayWithLessons.setLessons(Collections.singletonList(lesson));
        String formattedLessonString = "formatted lesson string";
        given(lessonFormatter.formatLesson(lesson)).willReturn(formattedLessonString);

        String formattedResult = dayFormatter.formatDay(dayWithLessons);

        assertThat(formattedResult, endsWith(formattedLessonString));
    }

    @Test
    public void ifThereAreNoLessonsThereAreExpectedPlaceholder() throws Exception {
        Day dayWithoutLessons = new Day();
        dayWithoutLessons.setDate(new Date());
        dayWithoutLessons.setLessons(Collections.emptyList());
        String expectedPlaceholder = "There are no lessons for this day!";

        String formattedResult = dayFormatter.formatDay(dayWithoutLessons);

        assertThat(formattedResult, endsWith(expectedPlaceholder));
    }

    private String getExpectedDayFormat(Date date) {
        return new SimpleDateFormat("EEEE, yyyy-MM-dd").format(date);
    }

}
