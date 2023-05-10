package pra.luis.eduapp.eduapp.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pra.luis.eduapp.eduapp.auth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
