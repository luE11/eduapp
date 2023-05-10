package pra.luis.eduapp.eduapp.persons.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.persons.model.PersonDTO;
import pra.luis.eduapp.eduapp.persons.service.PersonService;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/user")
public class PersonController {

    @Autowired
    private PersonService personService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public EntityModel<Person> get(@PathVariable(name = "id", required = false) Integer personId, Authentication authentication){
        int requiredPersonId = (personId != null) ? personId : getPersonIdFromAuth(authentication);
        Person person = personService.getById(requiredPersonId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find person with id '"+requiredPersonId+"'"));
        return EntityModel.of(person,
                linkTo(methodOn(PersonController.class).get(requiredPersonId, null)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    @RolesAllowed(value = "Admin")
    public EntityModel<Person> insert(@RequestBody @Valid PersonDTO personDTO) throws EntityWithExistingFieldException {
        Person newPerson = personService.insert(personDTO);
        return EntityModel.of(newPerson,
                linkTo(methodOn(PersonController.class).get(newPerson.getId(), null)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    @RolesAllowed(value = "Admin")
    public CollectionModel<Person> getAll(@And({
            @Spec(path = "firstName", params = "firstName", spec = Like.class),
            @Spec(path = "lastName", params = "lastName", spec = Like.class),
            @Spec(path = "birthDate", params = "birthDate", spec = Like.class)
    }) Specification<Person> spec, Pageable pageable){
        return CollectionModel.of(personService.findAll(spec, pageable),
                linkTo(methodOn(PersonController.class).getAll(spec, pageable)).withSelfRel());
    }

    private int getPersonIdFromAuth(Authentication authentication){
        return personService.getIdByUsername(authentication.getName());
    }

}
