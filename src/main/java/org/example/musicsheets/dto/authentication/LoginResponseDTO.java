package org.example.musicsheets.dto.authentication;

public record LoginResponseDTO(
        String username,
        String avatarUrl,
        String token
) {
}
