package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.users.GetOneUserResponseDTO;
import org.example.musicsheets.dto.users.UpdateWholeUserRequestDTO;
import org.example.musicsheets.facades.UserFacade;
import org.example.musicsheets.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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
                                                                 @Valid @RequestBody UpdateWholeUserRequestDTO updatedUserDto,
                                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userFacade.updateWholeUser(userId, updatedUserDto, userDetails));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        userFacade.deleteWholeUser(userId, userDetails);
        return ResponseEntity.noContent().build();
    }
}
