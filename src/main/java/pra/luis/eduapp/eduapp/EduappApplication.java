package pra.luis.eduapp.eduapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pra.luis.eduapp.eduapp.auth.model.Role;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.auth.model.UserDTO;
import pra.luis.eduapp.eduapp.auth.repository.RoleRepository;
import pra.luis.eduapp.eduapp.auth.service.UserService;
import pra.luis.eduapp.eduapp.persons.model.PersonDTO;
import pra.luis.eduapp.eduapp.persons.service.PersonService;

import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class EduappApplication implements CommandLineRunner {

	@Autowired
	public PersonService personService;

	@Autowired
	public RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(EduappApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception {
		PersonDTO person = new PersonDTO(new int[]{1,2}, "Luis", "Martínez", 1);
		try {
			personService.insert(person); // luis.martinez ]1sO-$N/*#
		}catch (Exception e){
			System.err.println(e.getMessage());
			System.err.println("Persona ya registrada. Omitiendo...");
		}
	}


}
