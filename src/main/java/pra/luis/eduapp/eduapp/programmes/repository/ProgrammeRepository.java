package pra.luis.eduapp.eduapp.programmes.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.programmes.model.Programme;

public interface ProgrammeRepository extends JpaRepository<Programme, Integer> {

    @NonNull
    Page<Programme> findAll(Specification<Person> spec, @NonNull Pageable pageable);
    boolean existsByName(@Param("name") String name);

}
