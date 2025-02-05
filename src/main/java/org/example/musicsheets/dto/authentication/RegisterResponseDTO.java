package org.example.musicsheets.dto.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record RegisterResponseDTO(
        String username,
        String avatarUrl,
        String token,

        @JsonIgnore
        Long id
) {
}
