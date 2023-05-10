package pra.luis.eduapp.eduapp;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pra.luis.eduapp.eduapp.auth.model.Role;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.auth.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException("User with username: "+ username + "was not found"));
        
        Set < GrantedAuthority > grantedAuthorities = new HashSet < > ();
        for(Role role: user.getRoles() ) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
            grantedAuthorities);
    }
}