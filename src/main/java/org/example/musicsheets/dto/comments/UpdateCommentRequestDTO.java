package org.example.musicsheets.dto.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateCommentRequestDTO(

        @Size(max = 1000, message = "Comment text cannot exceed 1000 characters")
        @NotBlank(message = "Comment text cannot be blank.")
        String text
) {
}
