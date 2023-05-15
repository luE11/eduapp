package pra.luis.eduapp.eduapp.programmes.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pra.luis.eduapp.eduapp.programmes.model.Programme;
import pra.luis.eduapp.eduapp.programmes.model.ProgrammeDTO;
import pra.luis.eduapp.eduapp.programmes.services.ProgrammeService;
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<Programme> insert(@Valid @ModelAttribute ProgrammeDTO programmeDTO)
                            throws EntityWithExistingFieldException, IOException {
        Programme programme = programmeService.insert(programmeDTO);
        return EntityModel.of(programme,
                linkTo(methodOn(ProgrammeController.class).insert(programmeDTO)).withSelfRel());
    }

}
