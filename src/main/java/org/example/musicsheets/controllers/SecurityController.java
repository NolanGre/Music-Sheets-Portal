package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.authentication.LoginRequestDTO;
import org.example.musicsheets.dto.authentication.LoginResponseDTO;
import org.example.musicsheets.dto.authentication.RegisterRequestDTO;
import org.example.musicsheets.dto.authentication.RegisterResponseDTO;
import org.example.musicsheets.facades.SecurityFacade;
import org.example.musicsheets.validation.ValidFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SecurityController {

    private final SecurityFacade securityFacade;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(securityFacade.login(loginRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestPart("data") RegisterRequestDTO data,
                                                        @Valid @ValidFile @RequestPart("file") MultipartFile file) {
        RegisterResponseDTO responseDTO = securityFacade.register(data, file);
        URI location = URI.create("/api/v1/users/" + responseDTO.id());

        return ResponseEntity.created(location).body(responseDTO);
    }
}
