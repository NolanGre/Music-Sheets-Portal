package org.example.musicsheets.security;

import lombok.AllArgsConstructor;
import org.example.musicsheets.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String login(String login, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(login);

        return jwtService.generateJwtToken(userDetails);
    }

    public String register(User user) {
        return jwtService.generateJwtToken(customUserDetailsService.loadUserByUsername(user.getLogin()));
    }
}
