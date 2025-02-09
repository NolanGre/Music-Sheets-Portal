package org.example.musicsheets.dto.sheets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.musicsheets.models.Genre;
import org.example.musicsheets.validation.ValidEnum;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateSheetRequestDTO(

        @NotBlank(message = "Title cannot be blank")
        @Size(max = 50, message = "Title cannot exceed ")
        String title,

        @NotBlank(message = "Author cannot be blank")
        @Size(max = 50, message = "Author cannot exceed 50 characters")
        String author,

        @Size(message = "Description text cannot exceed 1000 characters", max = 1000)
        String description,

        @ValidEnum(enumClass = Genre.class, message = "Invalid genre. Must be one of Genre enum")
        String genre
) {
}
