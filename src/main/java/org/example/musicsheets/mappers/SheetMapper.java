package org.example.musicsheets.mappers;

import jakarta.validation.Valid;
import org.example.musicsheets.dto.sheets.CreateSheetRequestDTO;
import org.example.musicsheets.dto.sheets.UpdateSheetRequestDTO;
import org.example.musicsheets.dto.sheets.WholeSheetResponseDTO;
import org.example.musicsheets.models.Comment;
import org.example.musicsheets.models.Genre;
import org.example.musicsheets.models.Like;
import org.example.musicsheets.models.Sheet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SheetMapper {

    @Mapping(source = "genre", target = "genre", qualifiedByName = "stringToGenre")
    Sheet createSheetRequestDTOtoSheet(CreateSheetRequestDTO createSheetRequestDTO);

    @Mapping(source = "genre", target = "genre", qualifiedByName = "stringToGenre")
    Sheet updateSheetRequestDTOtoSheet(UpdateSheetRequestDTO updateSheetRequestDTO);

    @Mapping(source = "likes", target = "likesCount", qualifiedByName = "calculateLikesCount")
    @Mapping(source = "comments", target = "commentsCount", qualifiedByName = "calculateCommentsCount")
    WholeSheetResponseDTO sheetToWholeSheetResponseDTO(Sheet sheet);

    @Named("stringToGenre")
    default Genre stringToGenre(String genre) {
        if (genre == null) {
            return null;
        }
        return Genre.valueOf(genre.toUpperCase());
    }

    @Named("calculateLikesCount")
    default Integer calculateLikesCount(List<Like> likes) {
        return likes != null ? likes.size() : 0;
    }

    @Named("calculateCommentsCount")
    default Integer calculateCommentsCount(List<Comment> comments) {
        return comments != null ? comments.size() : 0;
    }
}
