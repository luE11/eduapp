package pra.luis.eduapp.eduapp.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pra.luis.eduapp.eduapp.auth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT id FROM Role WHERE LOWER(name)=LOWER(:name)")
    int getIdByRoleName(@Param("name") String name);
}
