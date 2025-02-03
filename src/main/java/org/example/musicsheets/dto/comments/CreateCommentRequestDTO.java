package org.example.musicsheets.dto.comments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequestDTO(

        @Size(max = 1000, message = "Comment text cannot exceed 1000 characters")
        @NotBlank(message = "Comment text cannot be blank.")
        String text
) {
}
