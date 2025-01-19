package org.example.musicsheets.dto.sheets;

import org.example.musicsheets.dto.users.PublisherUserResponseDTO;
import org.example.musicsheets.models.Genre;

import java.util.Date;

public record WholeSheetResponseDTO(
        Long id,
        String title,
        String author,
        String description,
        Genre genre,
        String fileUrl,
        Integer likesCount,
        Integer commentsCount,
        PublisherUserResponseDTO publisher,
        Date creatingDate,
        Date modifyingDate
) {
}
