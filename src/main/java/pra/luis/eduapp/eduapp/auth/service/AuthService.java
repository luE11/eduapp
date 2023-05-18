package pra.luis.eduapp.eduapp.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pra.luis.eduapp.eduapp.utils.JwtTokenUtil;
import pra.luis.eduapp.eduapp.UserDetailsServiceImpl;
import pra.luis.eduapp.eduapp.auth.model.User;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public User authenticate(String username, String password) throws BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Optional<User> user = userService.findByUsername(username);
        return user.orElse(null);
    }

    public String generateToken(String username) {
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    public Date getTokenExpirationDate(String token) {
        return jwtTokenUtil.getExpirationDateFromToken(token);
    }

}
