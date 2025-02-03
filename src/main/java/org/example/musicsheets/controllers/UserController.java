package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.users.GetOneUserResponseDTO;
import org.example.musicsheets.dto.users.UpdateWholeUserRequestDTO;
import org.example.musicsheets.mappers.UserMapper;
import org.example.musicsheets.models.User;
import org.example.musicsheets.security.CustomUserDetails;
import org.example.musicsheets.services.AuthorizationService;
import org.example.musicsheets.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final UserMapper userMapper;

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponseDTO> getUserById(@PathVariable Long userId,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(), userId, authenticatedUser.getRole());

        return ResponseEntity.ok(userMapper.userToGetOneUserResponseDTO(userService.getUserById(userId)));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponseDTO> updateWholeUser(@PathVariable Long userId,
                                                                 @Valid @RequestBody UpdateWholeUserRequestDTO updatedUserDto,
                                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(), userId, authenticatedUser.getRole());

        User updatedUser = userService.updateWholeUser(userId, userMapper.updatedUserDTOtoUser(updatedUserDto));

        return ResponseEntity.ok(userMapper.userToGetOneUserResponseDTO(updatedUser));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(), userId, authenticatedUser.getRole());

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
