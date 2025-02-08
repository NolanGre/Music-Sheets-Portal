package org.example.musicsheets.security;

import lombok.AllArgsConstructor;
import org.example.musicsheets.exceptions.UserNotFoundException;
import org.example.musicsheets.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String login(String login, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (AuthenticationException e) {
            throw new UserNotFoundException("User with login: " + login + " not found");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(login);

        return jwtService.generateJwtToken(userDetails);
    }

    public String register(User user) {
        return jwtService.generateJwtToken(customUserDetailsService.loadUserByUsername(user.getLogin()));
    }
}
