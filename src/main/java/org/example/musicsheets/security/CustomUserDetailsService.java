package org.example.musicsheets.security;

import lombok.AllArgsConstructor;
import org.example.musicsheets.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) {
        return new CustomUserDetails(userService.getUserByLogin(login));
    }
}
