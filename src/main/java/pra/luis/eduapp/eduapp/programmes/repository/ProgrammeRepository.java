package pra.luis.eduapp.eduapp.programmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.luis.eduapp.eduapp.programmes.model.Programme;

public interface ProgrammeRepository extends JpaRepository<Programme, Integer> {

    boolean existsByName(@Param("name") String name);
}
