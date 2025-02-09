package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.users.GetOneUserResponseDTO;
import org.example.musicsheets.dto.users.UpdateWholeUserRequestDTO;
import org.example.musicsheets.facades.UserFacade;
import org.example.musicsheets.security.CustomUserDetails;
import org.example.musicsheets.validation.ValidFile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponseDTO> getUserById(@PathVariable Long userId,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userFacade.getUserById(userId, userDetails));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponseDTO> updateWholeUser(@PathVariable Long userId,
                                                                 @Valid @RequestPart("data") UpdateWholeUserRequestDTO data,
                                                                 @Valid @ValidFile @RequestPart("file") MultipartFile file,
                                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userFacade.updateWholeUser(userId, data, file, userDetails));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        userFacade.deleteWholeUser(userId, userDetails);
        return ResponseEntity.noContent().build();
    }
}
