package pra.luis.eduapp.eduapp.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pra.luis.eduapp.eduapp.auth.model.Role;
import pra.luis.eduapp.eduapp.auth.repository.RoleRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAll(){
        return roleRepository.findAll();
    }


}
