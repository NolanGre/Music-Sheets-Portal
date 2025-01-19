package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import org.example.musicsheets.dto.users.UpdateWholeUserRequestDTO;
import org.example.musicsheets.mappers.UserMapper;
import org.example.musicsheets.models.User;
import org.example.musicsheets.security.CustomUserDetails;
import org.example.musicsheets.services.AuthorizationService;
import org.example.musicsheets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, AuthorizationService authorizationService, UserMapper userMapper) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        if (!authorizationService.isAdminOrOwner(authenticatedUser.getId(), userId, authenticatedUser.getRole())) {
            return ResponseEntity.status(403).body("You do not have permission to access this resource.");
        }

        return ResponseEntity.ok(userMapper.userToGetOneUserResponseDTO(userService.getUserById(userId)));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateWholeUser(@PathVariable("userId") Long userId,
                                             @Valid @RequestBody UpdateWholeUserRequestDTO updatedUserDto,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        if (!authorizationService.isAdminOrOwner(authenticatedUser.getId(), userId, authenticatedUser.getRole())) {
            return ResponseEntity.status(403).body("You do not have permission to update this resource.");
        }

        User updatedUser = userService.updateWholeUser(userId, userMapper.updatedUserDTOtoUser(updatedUserDto));

        return ResponseEntity.ok(userMapper.userToGetOneUserResponseDTO(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        if (!authorizationService.isAdminOrOwner(authenticatedUser.getId(), userId, authenticatedUser.getRole())) {
            return ResponseEntity.status(403).body("You do not have permission to delete this resource.");
        }

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
