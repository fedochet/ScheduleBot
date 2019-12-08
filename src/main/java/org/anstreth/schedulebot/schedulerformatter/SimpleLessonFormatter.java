package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.Auditory;
import org.anstreth.ruzapi.response.Lesson;
import org.anstreth.ruzapi.response.Teacher;
import org.springframework.stereotype.Component;

@Component
class SimpleLessonFormatter implements LessonFormatter {

    @Override
    public String formatLesson(Lesson lesson) {
        return String.format("%s - %s: %s (%s)\n%s\n%s",
                lesson.getTimeStart(),
                lesson.getTimeEnd(),
                lesson.getSubject(),
                lesson.getLessonType().getName(),
                getTeacher(lesson),
                getAuditory(lesson));
    }

    private String getTeacher(Lesson lesson) {
        if (lesson.getTeachers() == null || lesson.getTeachers().isEmpty()) {
            return "no teacher";
        }

        Teacher teacher = lesson.getTeachers().get(0);
        return String.format("[%s]", teacher.getFullName());
    }

    private String getAuditory(Lesson lesson) {
        if (lesson.getAuditories() == null || lesson.getAuditories().isEmpty()) {
            return "no auditory";
        }

        Auditory auditory = lesson.getAuditories().get(0);
        return String.format("%s, %s", auditory.getName(), auditory.getBuilding().getName());
    }

}
