package org.example.musicsheets.dto.users;

import java.util.Date;

public record GetOneUserResponseDTO (
        String login,
        String username,
        String avatarUrl,
        Date creationDate
) {}
