package pra.luis.eduapp.eduapp.programmes.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pra.luis.eduapp.eduapp.persons.controller.PersonController;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.programmes.model.FullProgrammeDTO;
import pra.luis.eduapp.eduapp.programmes.model.Programme;
import pra.luis.eduapp.eduapp.programmes.model.ProgrammeDTO;
import pra.luis.eduapp.eduapp.programmes.services.ProgrammeService;
import pra.luis.eduapp.eduapp.utils.EntityListResponse;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;

import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RequestMapping(value = "/api/programme")
@RestController
public class ProgrammeController {

    private final ProgrammeService programmeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public EntityModel<FullProgrammeDTO> get(@PathVariable(name = "id") Integer programmeId){
        Programme programme = programmeService.findById(programmeId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find programme with id '"+programmeId+"'"));
        return EntityModel.of(new FullProgrammeDTO(programme),
                linkTo(methodOn(ProgrammeController.class).get(programmeId)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public EntityModel<EntityListResponse<Programme>> getAll(
            @And({
                    @Spec(path = "name", params = "name", spec = Like.class)
            }) Specification<Programme> spec, Pageable pageable){
        Page<Programme> results = programmeService.findAll(spec, pageable);
        return EntityModel.of(new EntityListResponse<>(results.getContent(), pageable.getPageNumber(),
                        pageable.getPageSize(), results.getTotalPages(), results.getTotalElements()),
                linkTo(methodOn(ProgrammeController.class).getAll(spec, pageable)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<Programme> insert(@Valid @ModelAttribute ProgrammeDTO programmeDTO)
                            throws EntityWithExistingFieldException, IOException {
        Programme programme = programmeService.insert(programmeDTO);
        return EntityModel.of(programme,
                linkTo(methodOn(ProgrammeController.class).insert(programmeDTO)).withSelfRel());
    }

}
