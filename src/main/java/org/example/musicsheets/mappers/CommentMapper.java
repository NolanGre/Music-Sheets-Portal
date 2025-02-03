package org.example.musicsheets.mappers;

import org.example.musicsheets.dto.comments.CommentResponseDTO;
import org.example.musicsheets.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(source = "sheet.id", target = "sheetId")
    @Mapping(source = "author.id", target = "userId")
    CommentResponseDTO commentToCommentResponseDTO(Comment comment);

    List<CommentResponseDTO> commentsListToCommentResponseDTOsList(List<Comment> comments);
}
