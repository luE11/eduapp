package pra.luis.eduapp.eduapp.integration.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import pra.luis.eduapp.eduapp.auth.model.Role;
import pra.luis.eduapp.eduapp.auth.service.RoleService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class RoleServiceTest {
    private final Role ROLESTUDENT = new Role(1, "Student");
    private final Role ROLEADMIN = new Role(2, "Admin");
    @Autowired
    private RoleService roleService;

    /**
     * Inserting data by calling 'user_roles_programme.sql' script from resources
     * \\@Sql directive
     */
    @Sql(scripts = "/user_roles_programme.sql")
    @Test
    public void it_should_get_all_roles(){
        List<Role> roles = new ArrayList<>(Arrays.asList(ROLESTUDENT, ROLEADMIN));
        assertThat(roleService.getAll()).hasSameElementsAs(roles);
    }

}