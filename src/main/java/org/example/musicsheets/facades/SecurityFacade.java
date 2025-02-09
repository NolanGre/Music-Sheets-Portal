package org.example.musicsheets.facades;

import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.authentication.LoginRequestDTO;
import org.example.musicsheets.dto.authentication.LoginResponseDTO;
import org.example.musicsheets.dto.authentication.RegisterRequestDTO;
import org.example.musicsheets.dto.authentication.RegisterResponseDTO;
import org.example.musicsheets.mappers.UserMapper;
import org.example.musicsheets.models.User;
import org.example.musicsheets.security.AuthenticationService;
import org.example.musicsheets.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Service
@AllArgsConstructor
public class SecurityFacade {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        String jwt = authenticationService.login(loginRequestDTO.login(), loginRequestDTO.password());
        User user = userService.getUserByLogin(loginRequestDTO.login());

        return userMapper.userAndTokenToLoginResponse(user, jwt);
    }

    public RegisterResponseDTO register(RegisterRequestDTO data, MultipartFile file) {
        User user = userService.createUser(userMapper.registerRequestDTOtoUser(data), file);

        String jwt = authenticationService.register(user);

        return userMapper.userAndTokenToRegisterResponse(user, jwt);
    }
}
