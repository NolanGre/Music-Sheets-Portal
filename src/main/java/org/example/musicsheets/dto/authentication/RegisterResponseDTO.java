package org.example.musicsheets.dto.authentication;

public record RegisterResponseDTO(
        String username,
        String avatarUrl,
        String token
) {
}
