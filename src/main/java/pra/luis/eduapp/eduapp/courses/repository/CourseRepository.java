package pra.luis.eduapp.eduapp.courses.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pra.luis.eduapp.eduapp.courses.model.Course;
import pra.luis.eduapp.eduapp.persons.model.Person;

/**
 * @author luE11 on 4/07/23
 */
public interface CourseRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {
    boolean existsByIdCode(@Param("idCode") String idCode);

    Course findByIdCode(@Param("idCode") String idCode);

    /*
    @Query("SELECT new pra.luis.eduapp.eduapp.courses.model.SubscribedCourseDTO(c.idCode, c.schedule, c.state, c.maxCapacity, c.subject.subjectName, " +
            "c.teacher.email, cs.finalGrade, cs.isSubscribed) " +
            "FROM Course c JOIN c.students cs WHERE cs.student=:student")
    Page<SubscribedCourseDTO> findAllCoursesByStudentId(@Param("student") Person student, @NonNull Pageable pageable);
    @Query("SELECT new pra.luis.eduapp.eduapp.courses.model.SubscribedCourseDTO(c.idCode, c.schedule, c.state, c.maxCapacity, c.subject.subjectName, " +
            "c.teacher.email, cs.finalGrade, cs.isSubscribed) " +
            "FROM Course c JOIN c.students cs WHERE c.state='ACTIVE' AND cs.student=:student")
    Page<SubscribedCourseDTO> findAllActiveCoursesByStudentId(@Param("student") Person student, @NonNull Pageable pageable);

     */
}