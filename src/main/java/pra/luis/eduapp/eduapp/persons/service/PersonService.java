package pra.luis.eduapp.eduapp.persons.service;

import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.auth.model.UserDTO;
import pra.luis.eduapp.eduapp.auth.service.UserService;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.persons.model.PersonDTO;
import pra.luis.eduapp.eduapp.persons.repository.PersonRepository;
import pra.luis.eduapp.eduapp.programmes.model.Programme;
import pra.luis.eduapp.eduapp.programmes.services.ProgrammeService;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;
import pra.luis.eduapp.eduapp.utils.MyStringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProgrammeService programmeService;

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = { EntityWithExistingFieldException.class, EntityNotFoundException.class })
    public Person insert(PersonDTO personDTO) throws EntityWithExistingFieldException {
        if(isEmailAlreadyRegistered(personDTO.getEmail()))
            throw new EntityWithExistingFieldException("The email "+personDTO.getEmail()+" is already registered");
        User newUser = generateAndInsertUser(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getRoles());
        Person newPerson = generatePerson(personDTO, newUser);
        return personRepository.save(newPerson);
    }

    public Page<Person> findAll(Specification<Person> spec, Pageable pageable){
        return personRepository.findAll(spec, Objects.requireNonNullElseGet(pageable, Pageable::unpaged));
    }

    public Optional<Person> getById(int id){
        return personRepository.findById(id);
    }

    public int getIdByUsername(String username){
        return personRepository.getIdByUsername(username);
    }

    private User insertUser(String[] credentials, int[] roles) throws EntityWithExistingFieldException {
        UserDTO user = new UserDTO(credentials[0], credentials[1], roles);
        return userService.insertUserWithRoles(user);
    }
    private Person generatePerson(PersonDTO personDTO, User newUser){
        Person newPerson = personDTO.toPersonObject();
        newPerson.setUser(newUser);
        Programme programme = programmeService.findById(personDTO.getProgrammeId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Unable to find programme with id '"+personDTO.getProgrammeId()+"'"));
        newPerson.setProgramme(programme);
        return newPerson;
    }

    private User generateAndInsertUser(String firstName, String lastName, int[] roles) throws EntityWithExistingFieldException {
        String[] credentials = generateNewCredentials(firstName, lastName);
        return insertUser(credentials, roles);
    }

    private boolean isEmailAlreadyRegistered(String email){
        return email!=null && personRepository.existsByEmail(email);
    }

    /**
     * Generates unique username and password by using first and last person name
     * @param firstName person's first name
     * @param lastName person's last name
     * @return String array where array[0] -> username and array[1] -> password
     */
    private String[] generateNewCredentials(String firstName, String lastName){
        String[] credentials = new String[2];
        String lastUsername = personRepository.getUsernameOfLastPersonByFirstAndLastName(firstName, lastName);
        String usernameWithoutId = firstName.toLowerCase() + "." + lastName.toLowerCase();
        if(lastUsername!=null){
            String lastUsernameNumber = lastUsername.replace(usernameWithoutId,"");
            credentials[0] = usernameWithoutId
                    +( lastUsernameNumber.length()>0 ? Integer.parseInt(lastUsernameNumber)+1 : 1);
        }else
            credentials[0] = usernameWithoutId;
        credentials[0] = StringUtils.stripAccents(credentials[0]);
        credentials[1] = MyStringUtils.generateRandomString(10);
        return credentials;
    }

}
