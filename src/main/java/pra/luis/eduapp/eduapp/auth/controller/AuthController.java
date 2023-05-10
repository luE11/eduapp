package pra.luis.eduapp.eduapp.auth.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pra.luis.eduapp.eduapp.JwtTokenUtil;
import pra.luis.eduapp.eduapp.UserDetailsServiceImpl;
import pra.luis.eduapp.eduapp.auth.model.JwtRequest;
import pra.luis.eduapp.eduapp.auth.model.JwtResponse;
import pra.luis.eduapp.eduapp.auth.service.AuthService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/")
    public EntityModel<JwtResponse> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {
        authService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        String token = authService.generateToken(authenticationRequest.getUsername());
        return EntityModel.of(new JwtResponse(token),
                linkTo(methodOn(AuthController.class).createAuthenticationToken(authenticationRequest)).withSelfRel());
    }



}
