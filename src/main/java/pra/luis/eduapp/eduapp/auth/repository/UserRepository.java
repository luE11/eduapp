package pra.luis.eduapp.eduapp.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pra.luis.eduapp.eduapp.auth.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username); //removed @Param annotation
    boolean existsByUsername(String username);

    Optional<User> findByPersonId(int personId);
}
