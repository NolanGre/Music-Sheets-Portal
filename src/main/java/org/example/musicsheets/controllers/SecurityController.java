package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.authentication.LoginRequestDTO;
import org.example.musicsheets.dto.authentication.LoginResponseDTO;
import org.example.musicsheets.dto.authentication.RegisterRequestDTO;
import org.example.musicsheets.dto.authentication.RegisterResponseDTO;
import org.example.musicsheets.mappers.UserMapper;
import org.example.musicsheets.models.User;
import org.example.musicsheets.security.AuthenticationService;
import org.example.musicsheets.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SecurityController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        String jwt = authenticationService.login(loginRequestDTO.login(), loginRequestDTO.password());
        User user = userService.getUserByLogin(loginRequestDTO.login());

        return ResponseEntity.ok(userMapper.userAndTokenToLoginResponse(user, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        User user = userService.createUser(userMapper.registerRequestDTOtoUser(registerRequestDTO));

        String jwt = authenticationService.register(user);
        URI location = URI.create("/api/v1/users/" + user.getId());

        return ResponseEntity.created(location).body(userMapper.userAndTokenToRegisterResponse(user, jwt));
    }
}
