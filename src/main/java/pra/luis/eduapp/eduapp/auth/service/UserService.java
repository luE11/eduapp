package pra.luis.eduapp.eduapp.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pra.luis.eduapp.eduapp.auth.model.Role;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.auth.model.UserDTO;
import pra.luis.eduapp.eduapp.auth.repository.RoleRepository;
import pra.luis.eduapp.eduapp.auth.repository.UserRepository;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User insert(User user) throws EntityWithExistingFieldException {
        if ( userRepository.existsByUsername(user.getUsername()) )
            throw new EntityWithExistingFieldException("Username is already registered");
        System.out.println(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User insertUserWithRoles(UserDTO user) throws EntityWithExistingFieldException {
        User newUser = user.toUser();
        Iterable<Integer> roleIds = () -> Arrays.stream(user.getRoles()).iterator();
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        newUser.setRoles(roles);
        return insert(newUser);
    }


}
