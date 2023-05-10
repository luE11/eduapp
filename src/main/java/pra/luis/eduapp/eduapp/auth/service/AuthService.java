package pra.luis.eduapp.eduapp.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pra.luis.eduapp.eduapp.JwtTokenUtil;
import pra.luis.eduapp.eduapp.UserDetailsServiceImpl;

@Service
public class AuthService {

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public void authenticate(String username, String password) throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    public String generateToken(String username) {
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

}
