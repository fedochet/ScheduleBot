package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.Day;
import org.anstreth.ruzapi.response.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
class SimpleSchedulerFormatter implements SchedulerFormatter {

    private final LessonFormatter lessonFormatter;

    @Autowired
    public SimpleSchedulerFormatter(LessonFormatter lessonFormatter) {
        this.lessonFormatter = lessonFormatter;
    }

    @Override
    public String getNoScheduleForDateMessage(Calendar calendar) {
        return "There are no lessons!";
    }

    @Override
    public String formatDay(Day scheduleForToday) {
        String lessons = getLessonsString(scheduleForToday.getLessons());
        return String.format("Schedule for day %s:\n\n%s", scheduleForToday.getDate(), lessons);
    }

    private String getLessonsString(List<Lesson> lessons) {
        if (lessons.isEmpty()) {
            return "There are no lessons for this day!";
        }

        return lessons.stream()
                .map(lessonFormatter::formatLesson)
                .collect(Collectors.joining("\n\n"));
    }

}