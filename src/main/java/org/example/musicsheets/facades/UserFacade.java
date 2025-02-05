package org.example.musicsheets.facades;

import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.users.GetOneUserResponseDTO;
import org.example.musicsheets.dto.users.UpdateWholeUserRequestDTO;
import org.example.musicsheets.mappers.UserMapper;
import org.example.musicsheets.models.User;
import org.example.musicsheets.security.CustomUserDetails;
import org.example.musicsheets.services.AuthorizationService;
import org.example.musicsheets.services.UserService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final UserMapper userMapper;

    public GetOneUserResponseDTO getUserById(Long userId, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(), userId, authenticatedUser.getRole());

        return userMapper.userToGetOneUserResponseDTO(userService.getUserById(userId));
    }

    public GetOneUserResponseDTO updateWholeUser(Long userId, UpdateWholeUserRequestDTO updatedUserDto, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(), userId, authenticatedUser.getRole());

        User updatedUser = userService.updateWholeUser(userId, userMapper.updatedUserDTOtoUser(updatedUserDto));

        return userMapper.userToGetOneUserResponseDTO(updatedUser);
    }

    public void deleteWholeUser(Long userId, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(), userId, authenticatedUser.getRole());

        userService.deleteUser(userId);
    }
}
