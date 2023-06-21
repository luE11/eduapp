package pra.luis.eduapp.eduapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import pra.luis.eduapp.eduapp.persons.model.ExtendedPersonDTO;
import pra.luis.eduapp.eduapp.persons.service.PersonService;

@Configuration
public class ApplicationInitSeeder implements CommandLineRunner {

    @Autowired
    public PersonService personService;

    private void createTestUser(){
        ExtendedPersonDTO person = new ExtendedPersonDTO(new int[]{1,2}, "Luis", "Martínez", 1);
        try {
            personService.insert(person); // luis.martinez ]1sO-$N/*#
        }catch (Exception e){
            System.err.println(e.getMessage());
            System.err.println("Persona ya registrada. Omitiendo...");
            System.err.println("]1sO-$N/*#");
        }
    }

    @Override
    public void run(String...args) throws Exception {
        createTestUser();
    }
}
