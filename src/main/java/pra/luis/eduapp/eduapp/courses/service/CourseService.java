package pra.luis.eduapp.eduapp.courses.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pra.luis.eduapp.eduapp.courses.model.*;
import pra.luis.eduapp.eduapp.courses.repository.CourseRepository;
import pra.luis.eduapp.eduapp.courses.repository.CourseStudentRepository;
import pra.luis.eduapp.eduapp.courses.repository.StudentRelatedCoursesViewRepository;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.persons.service.PersonService;
import pra.luis.eduapp.eduapp.subjects.model.Subject;
import pra.luis.eduapp.eduapp.subjects.service.SubjectService;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;
import pra.luis.eduapp.eduapp.utils.OperationNotAllowedException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author luE11 on 4/07/23
 */
@AllArgsConstructor
@Service
public class CourseService {

    private CourseRepository courseRepository;
    private SubjectService subjectService;
    private PersonService personService;
    private CourseStudentRepository courseStudentRepository;
    private StudentRelatedCoursesViewRepository studentRelatedCoursesViewRepository;

    public Course insert(CourseDTO courseDTO) throws EntityWithExistingFieldException {
        if(courseExists(courseDTO))
            throw new EntityWithExistingFieldException("The course with id code and subject is already used by another record");
        Subject subject = subjectService.getSubjectById(courseDTO.getSubjectId());
        Person teacher = personService.getById(courseDTO.getTeacherId());
        Course course = courseDTO.toCourse();
        course.setSubject(subject);
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    public Course get(int id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find course of id '"+id+"'"));
    }

    public Page<Course> findAll(Specification<Course> spec, Pageable pageable){
        return courseRepository.findAll(spec, Objects.requireNonNullElseGet(pageable, Pageable::unpaged));
    }

    public ExtendedCourseDTO getFullCourse(int id){
        return new ExtendedCourseDTO(this.get(id));
    }

    // Check person has student role in controller
    // /api/courses/1/subscribe/2
    public void subscribe(int courseId, int studentId) throws EntityWithExistingFieldException {
        Optional<CourseHasStudent> courseHasStudent = courseStudentRepository.findById(new CourseStudentId(courseId, studentId));
        if(courseHasStudent.isPresent()){
            throw new EntityWithExistingFieldException("Student with id "+studentId+" is already registered into course of id "+courseId);
        }else {
            CourseHasStudent chs = new CourseHasStudent(get(courseId), personService.getById(studentId));
            courseStudentRepository.save(chs);
        }
    }

    public void unSubscribe(int courseId, int studentId) {
        CourseHasStudent courseHasStudent = courseStudentRepository.findById(new CourseStudentId(courseId, studentId))
                .orElseThrow(() -> new EntityNotFoundException("Student with id "+studentId+" is not subscribed to course with id "+courseId));
        courseHasStudent.setSubscribed(false);
        courseStudentRepository.save(courseHasStudent);
    }

    public Course update(int id, CourseDTO courseDTO){
        Course course = get(id);
        course.updateBaseProperties(courseDTO);
        return courseRepository.save(course);
    }

    public void delete(int id){
        Course course = get(id);
        courseRepository.delete(course);
    }

    /**
     * Get all courses subscribed by a student
     * @param studentId
     * @param pageable
     * @return
     */
    public Page<StudentRelatedCoursesView> getStudentCoursesHistory(int studentId, Pageable pageable){
        return studentRelatedCoursesViewRepository.findAllByStudentId(studentId,
                Objects.requireNonNullElseGet(pageable, Pageable::unpaged));
    }

    /**
     * Active subscribed courses by a student
     * @param studentId
     * @param pageable
     * @return
     */
    public Page<StudentRelatedCoursesView> getAllSubscribed(int studentId, Pageable pageable){
        return studentRelatedCoursesViewRepository.findAllByStudentIdAndState(studentId, CourseState.ACTIVE,
                Objects.requireNonNullElseGet(pageable, Pageable::unpaged));
    }

    // course/finalgrade
    public void setFinalGrade(CourseFinalGradeDTO courseFinalGradeDTO) throws OperationNotAllowedException {
        if(!courseIsActive(courseFinalGradeDTO.getCourseId()))
            throw new OperationNotAllowedException("Course with id "+courseFinalGradeDTO.getCourseId()+" is not ACTIVE");
        CourseHasStudent courseHasStudent = courseStudentRepository.findById(
                new CourseStudentId(courseFinalGradeDTO.getCourseId(), courseFinalGradeDTO.getPersonId()))
                .orElseThrow(() -> new EntityNotFoundException("Student with id "+courseFinalGradeDTO.getPersonId()+
                        " is not subscribed to course with id "+courseFinalGradeDTO.getCourseId()));
        courseHasStudent.setFinalGrade(courseFinalGradeDTO.getFinalGrade());
        courseStudentRepository.save(courseHasStudent);
    }

    private boolean courseExists(CourseDTO courseDTO){
        Course c = courseRepository.findByIdCode(courseDTO.getIdCode());
        return c != null && c.getSubject().getId() == courseDTO.getSubjectId();
    }

    private boolean courseIsActive(int courseId){
        Course c = get(courseId);
        return c.getState().compareTo(CourseState.ACTIVE)==0;
    }
}