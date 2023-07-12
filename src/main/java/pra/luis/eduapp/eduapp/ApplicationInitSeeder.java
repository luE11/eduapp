package pra.luis.eduapp.eduapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import pra.luis.eduapp.eduapp.courses.model.CourseDTO;
import pra.luis.eduapp.eduapp.courses.service.CourseService;
import pra.luis.eduapp.eduapp.persons.model.ExtendedPersonDTO;
import pra.luis.eduapp.eduapp.persons.service.PersonService;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;

@Configuration
public class ApplicationInitSeeder implements CommandLineRunner {

    @Autowired public PersonService personService;
    @Autowired private CourseService courseService;

    private void createTestAdmin(){
        ExtendedPersonDTO person = new ExtendedPersonDTO(new int[]{1,2,3}, "Pepe",
                "Pérez", "mail@gmail.com", 1);
        try {
            personService.insert(person); // pepe.perez ]}:_ZU0kN(
        }catch (Exception e){
            System.err.println("Persona admin ya registrada. Omitiendo... ]}:_ZU0kN(");
        }
    }

    private void createTestStudent() throws EntityWithExistingFieldException {
        ExtendedPersonDTO person = new ExtendedPersonDTO(new int[]{1}, "Pepo",
                "Pérez", "mail2@gmail.com", 1);
        try {
            personService.insert(person); // pepo.perez (PLq[KIbU!
            CourseDTO courseDTO = new CourseDTO("1-0",
                    "MARTES: 8-10 RA-301\nJUEVES: 10-12 L-301", "ACTIVE", 20, 1, 1);
            courseService.insert(courseDTO);
            courseService.subscribe(1, 2);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Persona estudiante ya registrada. Omitiendo... (PLq[KIbU!");
        }
    }

    @Override
    public void run(String...args) throws Exception {
        createTestAdmin();
        createTestStudent();
    }
}
