package pra.luis.eduapp.eduapp.courses.repository;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import pra.luis.eduapp.eduapp.courses.model.CourseState;
import pra.luis.eduapp.eduapp.courses.model.StudentRelatedCoursesView;

/**
 * @author luE11 on 12/07/23
 */
public interface StudentRelatedCoursesViewRepository extends JpaRepository<StudentRelatedCoursesView, Integer>, JpaSpecificationExecutor<StudentRelatedCoursesView> {

    @NonNull
    Page<StudentRelatedCoursesView> findAllByStudentId(@Param("studentId") int studentId,
                                                       @NonNull Pageable pageable);
    @NonNull
    Page<StudentRelatedCoursesView> findAllByStudentIdAndState(@Param("studentId") int studentId,
                                                               @Param("state") CourseState courseState,
                                                               @NonNull Pageable pageable);

}
