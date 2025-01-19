package org.example.musicsheets.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

        @NotBlank(message = "Login cannot be blank")
        @Size(max = 50, message = "Login cannot exceed 50 characters")
        String login,

        @NotBlank(message = "Password cannot be blank")
        @Size(max = 20, message = "Login cannot exceed 50 characters")
        String password
) {
}
