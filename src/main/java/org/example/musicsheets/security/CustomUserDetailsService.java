package org.example.musicsheets.security;

import lombok.AllArgsConstructor;
import org.example.musicsheets.exceptions.UserNotFoundException;
import org.example.musicsheets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        return new CustomUserDetails(userService.getUserByLogin(login));
    }
}
