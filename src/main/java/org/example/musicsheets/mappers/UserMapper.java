package org.example.musicsheets.mappers;

import org.example.musicsheets.dto.authentication.LoginResponseDTO;
import org.example.musicsheets.dto.authentication.RegisterRequestDTO;
import org.example.musicsheets.dto.authentication.RegisterResponseDTO;
import org.example.musicsheets.dto.users.GetOneUserResponseDTO;
import org.example.musicsheets.dto.users.UpdateWholeUserRequestDTO;
import org.example.musicsheets.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User registerRequestDTOtoUser(RegisterRequestDTO registerRequestDTO);

    User updatedUserDTOtoUser(UpdateWholeUserRequestDTO updatedUserDto);

    LoginResponseDTO userAndTokenToLoginResponse(User user, String token);

    RegisterResponseDTO userAndTokenToRegisterResponse(User user, String token);

    GetOneUserResponseDTO userToGetOneUserResponseDTO(User user);

}
