package pra.luis.eduapp.eduapp.courses.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pra.luis.eduapp.eduapp.courses.model.*;
import pra.luis.eduapp.eduapp.courses.service.CourseService;
import pra.luis.eduapp.eduapp.persons.service.PersonService;
import pra.luis.eduapp.eduapp.utils.EntityListResponse;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;
import pra.luis.eduapp.eduapp.utils.OperationNotAllowedException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author luE11 on 5/07/23
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/course")
public class CourseController {

    private CourseService courseService;
    private PersonService personService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}")
    public EntityModel<ExtendedCourseDTO> get(@PathVariable(name = "id") Integer id){
        ExtendedCourseDTO course = courseService.getFullCourse(id);
        return EntityModel.of(course,
                linkTo(methodOn(CourseController.class).get(id)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public EntityModel<EntityListResponse<Course>> getAll(
            @And({
                    @Spec(path = "idCode", params = "idCode", spec = Like.class),
                    @Spec(path = "schedule", params = "schedule", spec = Like.class),
                    @Spec(path = "state", params = "state", spec = Like.class),
                    @Spec(path = "maxCapacity", params = "maxCapacity", spec = LessThanOrEqual.class),
                    @Spec(path = "subject", params = "subject", spec = Equal.class),
                    @Spec(path = "teacher", params = "teacher", spec = Equal.class)
            }) Specification<Course> spec, Pageable pageable
    ){
        Page<Course> result = courseService.findAll(spec, pageable);
        return EntityModel.of(new EntityListResponse<>(result.getContent(), pageable.getPageNumber(),
                pageable.getPageSize(), result.getTotalPages(), result.getTotalElements()),
                linkTo(methodOn(CourseController.class).getAll(spec, pageable)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<Course> insert(@Valid @RequestBody CourseDTO courseDTO) throws EntityWithExistingFieldException {
        Course course = courseService.insert(courseDTO);
        return EntityModel.of(course,
                linkTo(methodOn(CourseController.class).get(course.getId())).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public EntityModel<Course> update(@Valid @RequestBody CourseDTO courseDTO,
                                @PathVariable(name = "id") Integer id) throws EntityWithExistingFieldException {
        Course course = courseService.update(id, courseDTO);
        return EntityModel.of(course,
                linkTo(methodOn(CourseController.class).get(course.getId())).withSelfRel(),
                linkTo(methodOn(CourseController.class).getAll(null, null)).withRel("courses"),
                linkTo(methodOn(CourseController.class).update(courseDTO, course.getId())).withRel("update"));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> delete(@PathVariable(name = "id", required = true) Integer courseId) {
        courseService.delete(courseId);
        return ResponseEntity.noContent().build();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id}/subscribe")
    @PreAuthorize("hasAuthority('Student')")
    public ResponseEntity<?> subscribe(@PathVariable(name = "id") Integer courseId, Authentication auth) throws EntityWithExistingFieldException {
        int personId = getPersonIdFromAuth(auth);
        courseService.subscribe(courseId, personId);
        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id}/unsubscribe")
    @PreAuthorize("hasAuthority('Student')")
    public ResponseEntity<?> unSubscribe(@PathVariable(name = "id") Integer courseId, Authentication auth) throws EntityWithExistingFieldException {
        int personId = getPersonIdFromAuth(auth);
        courseService.unSubscribe(courseId, personId);
        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/history")
    @PreAuthorize("hasAuthority('Student')")
    public EntityModel<EntityListResponse<StudentRelatedCoursesView>> getHistory(Pageable pageable, Authentication auth){
        int personId = getPersonIdFromAuth(auth);
        Page<StudentRelatedCoursesView> results = courseService.getStudentCoursesHistory(personId, pageable);
        return EntityModel.of(new EntityListResponse<>(results.getContent(), pageable.getPageNumber(),
                        pageable.getPageSize(), results.getTotalPages(), results.getTotalElements()),
                linkTo(methodOn(CourseController.class).getHistory(pageable, auth)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/active")
    @PreAuthorize("hasAuthority('Student')")
    public EntityModel<EntityListResponse<StudentRelatedCoursesView>> getActive(Pageable pageable, Authentication auth){
        int personId = getPersonIdFromAuth(auth);
        Page<StudentRelatedCoursesView> result = courseService.getAllSubscribed(personId, pageable);
        return EntityModel.of(new EntityListResponse<>(result.getContent(), pageable.getPageNumber(),
                        pageable.getPageSize(), result.getTotalPages(), result.getTotalElements()),
                linkTo(methodOn(CourseController.class).getActive(pageable, auth)).withSelfRel());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/finalGrade")
    @PreAuthorize("hasAuthority('Teacher')")
    public ResponseEntity<?> SetFinalGrade(@Valid @RequestBody CourseFinalGradeDTO finalGradeDTO) throws EntityWithExistingFieldException, OperationNotAllowedException {
        courseService.setFinalGrade(finalGradeDTO);
        return ResponseEntity.ok().build();
    }

    private int getPersonIdFromAuth(Authentication authentication){
        return personService.getIdByUsername(authentication.getName());
    }

}
