package pra.luis.eduapp.eduapp.unit.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.auth.repository.RoleRepository;
import pra.luis.eduapp.eduapp.auth.repository.UserRepository;
import pra.luis.eduapp.eduapp.auth.service.UserService;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    UserService userService;

    private final User userTest = new User("luis.martinez", "1234");

    @BeforeEach
    void setUpService(){
        userService = new UserService(userRepository, roleRepository, bCryptPasswordEncoder);
    }
    @Test
    void should_insert_an_user_and_return_it() throws EntityWithExistingFieldException {
        mockSuccessfullyInsertUser(new User(1, "luis.martinez", "encrypted_1234"));
        User inserted = userService.insert(userTest);
        assertNotNull(inserted);
        assertNotEquals(0, inserted.getId());
        assertEquals(userTest.getUsername(), inserted.getUsername());
        /* password is encoded before inserting */
        assertNotEquals(userTest.getPassword(), inserted.getPassword());
    }

    @Test
    void should_throw_exception_on_insert_user_with_existing_username() throws EntityWithExistingFieldException {
        /* Insert once */
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        /* Try to insert twice with same username */
        Exception exception = assertThrows(EntityWithExistingFieldException.class,
                () -> userService.insert(userTest));
        assertEquals("Username is already registered", exception.getMessage());
    }

    @Test
    void findByUsername() throws EntityWithExistingFieldException {
        mockSuccessfullyInsertUser(userTest);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userTest));
        User insertedUser = userService.insert(userTest);
        Optional<User> finded = userService.findByUsername(userTest.getUsername());
        assertTrue(finded.isPresent());
        assertEquals(insertedUser, finded.get());
    }

    @Test
    void insertUserWithRoles() {

    }

    void mockSuccessfullyInsertUser(User user){
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
    }
}