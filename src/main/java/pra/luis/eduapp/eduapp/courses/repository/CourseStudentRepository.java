package pra.luis.eduapp.eduapp.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pra.luis.eduapp.eduapp.courses.model.Course;
import pra.luis.eduapp.eduapp.courses.model.CourseHasStudent;
import pra.luis.eduapp.eduapp.courses.model.CourseStudentId;

/**
 * @author luE11 on 10/07/23
 */
public interface CourseStudentRepository extends JpaRepository<CourseHasStudent, CourseStudentId>, JpaSpecificationExecutor<CourseHasStudent> {
}
