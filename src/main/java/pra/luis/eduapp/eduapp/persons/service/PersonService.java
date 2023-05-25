package pra.luis.eduapp.eduapp.persons.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pra.luis.eduapp.eduapp.auth.model.Role;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.auth.model.UserDTO;
import pra.luis.eduapp.eduapp.auth.service.RoleService;
import pra.luis.eduapp.eduapp.auth.service.UserService;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.persons.model.ExtendedPersonDTO;
import pra.luis.eduapp.eduapp.persons.repository.PersonRepository;
import pra.luis.eduapp.eduapp.programmes.model.Programme;
import pra.luis.eduapp.eduapp.programmes.services.ProgrammeService;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;
import pra.luis.eduapp.eduapp.utils.MyStringUtils;

import java.util.*;

@Service
public class PersonService {

    private final String ADMIN_ROLE = "Admin";
    private final String TEACHER_ROLE = "Teacher";
    private final String STUDENT_ROLE = "Student";

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProgrammeService programmeService;

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = { EntityWithExistingFieldException.class, EntityNotFoundException.class })
    public Person insert(ExtendedPersonDTO extendedPersonDTO) throws EntityWithExistingFieldException {
        if(isEmailAlreadyRegistered(extendedPersonDTO.getEmail()))
            throw new EntityWithExistingFieldException("The email "+ extendedPersonDTO.getEmail()+" is already registered");
        User newUser = generateAndInsertUser(extendedPersonDTO.getFirstName(), extendedPersonDTO.getLastName(), extendedPersonDTO.getRoles());
        Person newPerson = generatePerson(extendedPersonDTO, newUser);
        return personRepository.save(newPerson);
    }

    public Page<Person> findAll(Specification<Person> spec, Pageable pageable, int personId){
        User requestUser = userService.findByPersonId(personId);
        List<String> roles = Arrays.asList(requestUser.getRolesAsStringArray());
        Specification<Person> personSpec = (spec!=null) ? spec: Specification.where(null);
        if( roles.contains(ADMIN_ROLE) ){
            return personRepository.findAll(personSpec, Objects.requireNonNullElseGet(pageable, Pageable::unpaged));
        }else if ( roles.contains(TEACHER_ROLE) ){
            personSpec = personSpec.and((root, query, criteriaBuilder) -> {
                Join<User, Role> userRoles = root.join("user").join("roles");
               return criteriaBuilder.equal(userRoles.get("name"), STUDENT_ROLE);
            });
            return personRepository.findAll(personSpec,
                    Objects.requireNonNullElseGet(pageable, Pageable::unpaged));
        }
        return null;
    }

    public Person update(int personId, Person updatedPerson){
        return personRepository.findById(personId)
                .map( person -> {
                    person.updateProperties(updatedPerson);
                    return personRepository.save(person);
                }).orElseThrow(() -> new EntityNotFoundException("Can't update unregistered person"));
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
    private Person generatePerson(ExtendedPersonDTO extendedPersonDTO, User newUser){
        Person newPerson = extendedPersonDTO.toPersonObject();
        newPerson.setUser(newUser);
        Programme programme = programmeService.findById(extendedPersonDTO.getProgrammeId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Unable to find programme with id '"+ extendedPersonDTO.getProgrammeId()+"'"));
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
