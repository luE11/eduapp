package pra.luis.eduapp.eduapp.persons.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pra.luis.eduapp.eduapp.persons.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person> {
    boolean existsByEmail(@Param("email") String email);
    /*Person findFirstByFirstNameAndLastNameIgnoreCaseByOrderByIdDesc(@Param("firstName") String firstName,
                                      @Param("lastName") String lastName);
     */

    @Query(value = "SELECT LOWER(u.username) FROM User u, Person p WHERE u.id=p.user "+
        "AND LOWER(p.firstName)=LOWER(:firstName) AND LOWER(p.lastName)=LOWER(:lastName) "+
        "ORDER BY u.id LIMIT 1")
    String getUsernameOfLastPersonByFirstAndLastName(@Param("firstName") String firstName,
                                                     @Param("lastName") String lastName);

    @Query(value = "SELECT p.id FROM User u, Person p WHERE u.id=p.user "+
            "AND LOWER(u.username)=LOWER(:username)")
    int getIdByUsername(@Param("username") String username);
    @NonNull
    Page<Person> findAll(Specification<Person> spec, @NonNull Pageable pageable);
}
