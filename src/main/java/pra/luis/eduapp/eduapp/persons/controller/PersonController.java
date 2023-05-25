package pra.luis.eduapp.eduapp.persons.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.persons.model.ExtendedPersonDTO;
import pra.luis.eduapp.eduapp.persons.model.PersonDTO;
import pra.luis.eduapp.eduapp.persons.model.PersonListResponse;
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
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<Person> insert(@RequestBody @Valid ExtendedPersonDTO extendedPersonDTO) throws EntityWithExistingFieldException {
        Person newPerson = personService.insert(extendedPersonDTO);
        return EntityModel.of(newPerson,
                linkTo(methodOn(PersonController.class).get(newPerson.getId(), null)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<PersonListResponse> getAll(
            @And({
            @Spec(path = "firstName", params = "firstName", spec = Like.class),
            @Spec(path = "lastName", params = "lastName", spec = Like.class),
            @Spec(path = "email", params = "email", spec = Like.class)
        }) Specification<Person> spec, Pageable pageable, Authentication auth){
        Page<Person> results = personService.findAll(spec, pageable, getPersonIdFromAuth(auth));
        return EntityModel.of(new PersonListResponse(results.getContent(),
                        results.getTotalPages(), results.getTotalElements()),
                linkTo(methodOn(PersonController.class).getAll(spec, pageable, auth)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<Person> update(@PathVariable(name = "id", required = false) Integer personId,
                                      @RequestBody @Valid PersonDTO updatePersonDTO){
        Person updatedPerson = personService.update(personId, updatePersonDTO.toPersonObject());
        return EntityModel.of(updatedPerson,
                linkTo(methodOn(PersonController.class).update(personId, updatePersonDTO)).withSelfRel(),
                linkTo(methodOn(PersonController.class).get(personId, null)).withRel("user"),
                linkTo(methodOn(PersonController.class).getAll(null, null, null)).withRel("users"));
    }

    private int getPersonIdFromAuth(Authentication authentication){
        return personService.getIdByUsername(authentication.getName());
    }

}
