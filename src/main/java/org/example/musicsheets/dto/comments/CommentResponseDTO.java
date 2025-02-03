package org.example.musicsheets.dto.comments;

import java.util.Date;

public record CommentResponseDTO(
        Long id,
        Long sheetId,
        Long userId,
        String text,
        Date creatingDate,
        Date modifyingDate
) {
}
