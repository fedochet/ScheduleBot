package org.anstreth.schedulebot.schedulerformatter;

import org.anstreth.ruzapi.response.*;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleLessonFormatterTest {
    private SimpleLessonFormatter simpleLessonFormatter = new SimpleLessonFormatter();

    private final String NO_AUDITORIES_PLACEHOLDER = "no auditory";
    private final String NO_TEACHERS_PLACEHOLDER = "no teacher";

    @Test
    public void formatterConcatenatesSubjectAndTimesAndAuditoryName() throws Exception {
        String subjectName = "subject name";
        String timeStart = "time start";
        String timeEnd = "time end";
        String auditoryName = "auditory name";
        String auditoryBuilding = "auditory building";
        String lessonTypeName = "lesson type short name";
        String teacherFullName = "teacher name";

        Building building = getBuildingWithName(auditoryBuilding);
        Auditory auditory = getAuditoryWithNameAndBuilding(auditoryName, building);
        LessonType lessonType = getLessonTypeWithName(lessonTypeName);
        Teacher teacher = getTeacherWithFullName(teacherFullName);

        Lesson lesson = new Lesson();
        lesson.setSubject(subjectName);
        lesson.setTimeStart(timeStart);
        lesson.setTimeEnd(timeEnd);
        lesson.setAuditories(Collections.singletonList(auditory));
        lesson.setLessonType(lessonType);
        lesson.setTeachers(Collections.singletonList(teacher));

        String expectedFormat = String.format(
                "%s - %s: %s (%s)\n[%s]\n%s, %s",
                timeStart,
                timeEnd,
                subjectName,
                lessonTypeName,
                teacherFullName,
                auditoryName,
                auditoryBuilding
        );

        String formattedLesson = simpleLessonFormatter.formatLesson(lesson);

        assertThat(formattedLesson, is(expectedFormat));
    }

    @Test
    public void ifAuditoriesListIsEmptyPlaceholderIsPlacedInstead() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setLessonType(getLessonTypeWithName(""));
        lesson.setAuditories(Collections.emptyList());

        String formattedLesson = simpleLessonFormatter.formatLesson(lesson);

        assertThat(formattedLesson, endsWith(NO_AUDITORIES_PLACEHOLDER));
    }

    @Test
    public void ifAuditoriesListIsNullPlaceholderIsPlacedInstead() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setLessonType(getLessonTypeWithName(""));
        lesson.setAuditories(null);

        String formattedLesson = simpleLessonFormatter.formatLesson(lesson);

        assertThat(formattedLesson, endsWith(NO_AUDITORIES_PLACEHOLDER));
    }

    @Test
    public void ifTeacherListIsEmptyPlaceholderIsPlacedInstead() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setLessonType(getLessonTypeWithName(""));
        lesson.setTeachers(Collections.emptyList());

        String formattedLesson = simpleLessonFormatter.formatLesson(lesson);

        assertThat(formattedLesson, containsString(NO_TEACHERS_PLACEHOLDER));
    }

    @Test
    public void ifTeacherListIsNullPlaceholderIsPlacedInstead() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setLessonType(getLessonTypeWithName(""));
        lesson.setTeachers(null);

        String formattedLesson = simpleLessonFormatter.formatLesson(lesson);

        assertThat(formattedLesson, containsString(NO_TEACHERS_PLACEHOLDER));
    }

    private LessonType getLessonTypeWithName(String lessonTypeName) {
        LessonType lessonType = new LessonType();
        lessonType.setName(lessonTypeName);
        return lessonType;
    }

    private Building getBuildingWithName(String auditoryBuilding) {
        Building building = new Building();
        building.setName(auditoryBuilding);
        return building;
    }

    private Auditory getAuditoryWithNameAndBuilding(String auditoryName, Building building) {
        Auditory auditory = new Auditory();
        auditory.setName(auditoryName);
        auditory.setBuilding(building);
        return auditory;
    }

    private Teacher getTeacherWithFullName(String teacherFullName) {
        Teacher teacher = new Teacher();
        teacher.setFullName(teacherFullName);
        return teacher;
    }
}