package org.example.musicsheets.mappers;

import org.example.musicsheets.dto.likes.CheckLikeResponseDTO;
import org.example.musicsheets.models.Like;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LikeMapper {

    CheckLikeResponseDTO isLikeExistToCheckLikeResponseDTO(Boolean isLikeExist);
}