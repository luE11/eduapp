package pra.luis.eduapp.eduapp.subjects.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pra.luis.eduapp.eduapp.subjects.model.FullSubjectDTO;
import pra.luis.eduapp.eduapp.subjects.model.Subject;
import pra.luis.eduapp.eduapp.subjects.model.SubjectDTO;
import pra.luis.eduapp.eduapp.subjects.model.SubjectSubcribeDTO;
import pra.luis.eduapp.eduapp.subjects.service.SubjectService;
import pra.luis.eduapp.eduapp.utils.EntityListResponse;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author luE11 on 23/06/23
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/subject")
public class SubjectController {

    private final SubjectService subjectService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public EntityModel<FullSubjectDTO> get(@PathVariable(name = "id") Integer subjectId){
        FullSubjectDTO fullSubject = subjectService.findById(subjectId);
        return EntityModel.of(fullSubject,
                linkTo(methodOn(SubjectController.class).get(subjectId)).withSelfRel(),
                linkTo(methodOn(SubjectController.class).getAll(null, null)).withRel("subjects"));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public EntityModel<EntityListResponse<Subject>> getAll(
            @And({
                    @Spec(path = "subjectName", params = "subjectName", spec = Like.class),
                    @Spec(path = "credits", params = "credits", spec = Equal.class),
                    @Spec(path = "subscribable", params = "subscribable", spec = Equal.class),
                    @Spec(path = "programme", params = "programme", spec = Equal.class)
            }) Specification<Subject> spec, Pageable pageable){
        Page<Subject> results = subjectService.findAll(spec, pageable);
        return EntityModel.of(new EntityListResponse<>(results.getContent(), pageable.getPageNumber(),
                        pageable.getPageSize(), results.getTotalPages(), results.getTotalElements()),
                linkTo(methodOn(SubjectController.class).getAll(spec, pageable)).withSelfRel());
    }

    @SecurityRequirement(name= "Bearer Authentication")
    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<Subject> insert(@RequestBody @Valid SubjectDTO subjectDTO) throws EntityWithExistingFieldException {
        Subject newSubj = subjectService.insert(subjectDTO);
        return EntityModel.of(newSubj,
                linkTo(methodOn(SubjectController.class).get(newSubj.getId())).withSelfRel(),
                linkTo(methodOn(SubjectController.class).getAll(null, null)).withRel("subjects"));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<Subject> update(@PathVariable(name = "id") Integer subjectId,
                                       @Valid @RequestBody SubjectDTO subjectDTO) throws EntityWithExistingFieldException {
        Subject updSubj = subjectService.update(subjectId, subjectDTO.toSubject());
        return EntityModel.of(updSubj,
                linkTo(methodOn(SubjectController.class).get(updSubj.getId())).withSelfRel(),
                linkTo(methodOn(SubjectController.class).getAll(null, null)).withRel("subjects"));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<Subject> patchSubscribable(@PathVariable(name = "id") Integer subjectId,
                                                  @Valid @RequestBody SubjectSubcribeDTO subcribeDTO) {
        Subject patched = subjectService.patchSubscribable(subjectId, subcribeDTO);
        return EntityModel.of(patched,
                linkTo(methodOn(SubjectController.class).get(patched.getId())).withSelfRel(),
                linkTo(methodOn(SubjectController.class).getAll(null, null)).withRel("subjects"));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> delete(@PathVariable(name = "id", required = true) Integer subjectId) {
        subjectService.delete(subjectId);
        return ResponseEntity.noContent().build();
    }
}
