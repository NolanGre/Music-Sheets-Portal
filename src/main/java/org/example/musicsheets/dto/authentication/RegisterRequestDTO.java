package org.example.musicsheets.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

        @NotBlank(message = "Login cannot be blank")
        @Size(max = 50, message = "Login cannot exceed 50 characters")
        String login,

        @NotBlank(message = "Password cannot be blank")
        @Size(max = 20, message = "Login cannot exceed 50 characters")
        String password,

        @NotBlank(message = "Username cannot be blank")
        @Size(max = 20, message = "Username cannot exceed 50 characters")
        String username,

        @NotBlank(message = "AvatarUrl cannot be blank")
        String avatarUrl
) {
}
