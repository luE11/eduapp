package pra.luis.eduapp.eduapp.courses.model;

import lombok.Getter;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.subjects.model.Subject;
import java.util.Set;

/**
 * @author luE11 on 4/07/23
 */
@Getter
public class ExtendedCourseDTO extends BaseCourseDTO {

    private final Subject subject;
    private final Person teacher;
    private final int studentsRegistered;

    public ExtendedCourseDTO(Course course) {
        super(course.getIdCode(), course.getSchedule(), course.getState().getStateStr(), course.getMaxCapacity());
        this.subject = course.getSubject();
        this.teacher = course.getTeacher();
        this.studentsRegistered = course.getStudents().size();
    }
}
