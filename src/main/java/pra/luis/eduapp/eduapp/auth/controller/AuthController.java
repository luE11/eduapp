package pra.luis.eduapp.eduapp.auth.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import pra.luis.eduapp.eduapp.auth.model.*;
import pra.luis.eduapp.eduapp.auth.service.AuthService;
import pra.luis.eduapp.eduapp.auth.service.RefreshTokenService;
import pra.luis.eduapp.eduapp.utils.TokenRefreshException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/")
    public ResponseEntity<EntityModel<JwtResponse>> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) {
        User user = authService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        String token = authService.generateToken(authenticationRequest.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        JwtResponse response = new JwtResponse(token, user.getId(),
                user.getUsername(), user.getRolesAsStringArray(), authService.getTokenExpirationDate(token));
        HttpHeaders headers = generateHeadersWithRefreshTokenCookie(refreshToken);
        return ResponseEntity.ok()
                .headers(headers)
                .body(
                EntityModel.of(response,
            linkTo(methodOn(AuthController.class).createAuthenticationToken(authenticationRequest)).withSelfRel()));
    }

    @GetMapping("/refreshtoken")
    public EntityModel<RefreshJwtTokenResponse> refreshtoken(@CookieValue(name = "refresh_token") String refreshToken) throws TokenRefreshException {
        RefreshToken rToken = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new NotFoundException("Refresh token is not in database!"));
        refreshTokenService.verifyExpiration(rToken);
        String newToken = authService.generateToken(rToken.getUser().getUsername());
        RefreshJwtTokenResponse response = new RefreshJwtTokenResponse(newToken, authService.getTokenExpirationDate(newToken));
        return EntityModel.of(response);
    }

    public HttpHeaders generateHeadersWithRefreshTokenCookie(RefreshToken refreshToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                "refresh_token="+refreshToken.getToken()+";HttpOnly;Path=/api/auth/refreshtoken;SameSite=strict;Expires="+refreshToken.getExpirationDate());
        return headers;
    }

}
