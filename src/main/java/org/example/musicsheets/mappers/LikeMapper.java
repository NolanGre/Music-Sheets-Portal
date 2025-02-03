package org.example.musicsheets.mappers;

import org.example.musicsheets.dto.likes.CheckLikeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LikeMapper {

    CheckLikeResponseDTO isLikeExistToCheckLikeResponseDTO(Boolean isLikeExist);
}