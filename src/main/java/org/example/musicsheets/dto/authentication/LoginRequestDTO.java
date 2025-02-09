package org.example.musicsheets.dto.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequestDTO(

        @NotBlank(message = "Login cannot be blank")
        @Size(max = 50, message = "Login cannot exceed 50 characters")
        String login,

        @NotBlank(message = "Password cannot be blank")
        @Size(max = 20, message = "Login cannot exceed 50 characters")
        String password
) {
}
