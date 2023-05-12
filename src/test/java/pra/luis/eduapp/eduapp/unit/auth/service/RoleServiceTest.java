package pra.luis.eduapp.eduapp.unit.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pra.luis.eduapp.eduapp.auth.model.Role;
import pra.luis.eduapp.eduapp.auth.repository.RoleRepository;
import pra.luis.eduapp.eduapp.auth.service.RoleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    private final Role ROLESTUDENT = new Role(1, "Student");
    private final Role ROLEADMIN = new Role(2, "Admin");

    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUpRoleService(){
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void it_should_get_all_roles(){
        List<Role> roles = new ArrayList<>(Arrays.asList(ROLESTUDENT, ROLEADMIN));
        when(roleRepository.findAll()).thenReturn(roles);
        assertEquals(roleService.getAll(), roles);
        verify(roleRepository).findAll();
    }


}