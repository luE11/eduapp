package pra.luis.eduapp.eduapp.integration.programme.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pra.luis.eduapp.eduapp.programmes.model.Programme;
import pra.luis.eduapp.eduapp.programmes.repository.ProgrammeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author luis
 * Test programme JPA repository by using DataJpaTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(scripts = "/user_roles_programme.sql")
class ProgrammeRepositoryTest {

    @Autowired private ProgrammeRepository programmeRepository;

    @Test
    void injectedRepositoryNotNull(){
        assertNotNull(programmeRepository);
    }

    @Test
    void insert(){
        Programme programme = new Programme("Ingeniería de la vida", "fake_url");
        Programme inserted = programmeRepository.save(programme);
        assertNotNull(inserted);
        assertNotEquals(0, inserted.getId());
        assertEquals(programme.getName(), inserted.getName());
        assertEquals(programme.getLogoUrl(), inserted.getLogoUrl());
    }

    @Test
    void findAll() {
        List<Programme> programmes = programmeRepository.findAll();
        assertNotNull(programmes);
        assertFalse(programmes.isEmpty());
        // Checks first element has name Computer and Systems Engineering, inserted by sql at class level
        Programme first = programmes.get(0);
        assertNotNull(first);
        assertEquals("Computer and Systems Engineering", first.getName());
    }

    @Test
    void existsByName() {
        assertTrue(programmeRepository.existsByName("Computer and Systems Engineering"));
    }
}