package pra.luis.eduapp.eduapp.subjects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import pra.luis.eduapp.eduapp.subjects.model.Subject;

/**
 * @author luE11 on 22/06/23
 */
public interface SubjectRepository extends JpaRepository<Subject, Integer>, JpaSpecificationExecutor<Subject> {

    boolean existsBySubjectName(@Param("subjectName") String subjectName);

}
