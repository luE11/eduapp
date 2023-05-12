package pra.luis.eduapp.eduapp.auth.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pra.luis.eduapp.eduapp.auth.model.Role;
import pra.luis.eduapp.eduapp.auth.service.RoleService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Admin')")
    public CollectionModel<Role> get(){
        return CollectionModel.of(roleService.getAll(),
                linkTo(methodOn(RoleController.class).get()).withSelfRel());
    }


}
