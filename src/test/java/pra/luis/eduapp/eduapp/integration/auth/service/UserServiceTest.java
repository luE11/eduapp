package pra.luis.eduapp.eduapp.integration.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.auth.service.UserService;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    private final User user = new User("luis.martinez", "1234");
    @Test
    void should_insert_an_user_and_return_it() throws EntityWithExistingFieldException {
        User inserted = userService.insert(user);
        assertNotNull(inserted);
        assertNotEquals(0, inserted.getId());
        assertEquals(user.getUsername(), inserted.getUsername());
        /* password is encoded before inserting */
        assertNotEquals(user.getPassword(), inserted.getPassword());
    }

    @Test
    void should_throw_exception_on_insert_user_with_existing_username() throws EntityWithExistingFieldException {
        /* Insert once */
        userService.insert(user);
        /* Try to insert twice with same username */
        Exception exception = assertThrows(EntityWithExistingFieldException.class,
                () -> userService.insert(user));
        assertEquals("Username is already registered", exception.getMessage());
    }

    @Test
    void findByUsername() throws EntityWithExistingFieldException {
        User insertedUser = userService.insert(user);
        Optional<User> finded = userService.findByUsername(user.getUsername());
        assertTrue(finded.isPresent());
        assertEquals(insertedUser, finded.get());
    }

    @Test
    void insertUserWithRoles() {

    }
}