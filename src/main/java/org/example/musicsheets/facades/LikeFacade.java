package org.example.musicsheets.facades;

import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.likes.CheckLikeResponseDTO;
import org.example.musicsheets.mappers.LikeMapper;
import org.example.musicsheets.models.User;
import org.example.musicsheets.security.CustomUserDetails;
import org.example.musicsheets.services.LikeService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeFacade {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    public CheckLikeResponseDTO checkLike(Long sheetId, CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        return likeMapper
                .isLikeExistToCheckLikeResponseDTO(likeService.doesLikeExist(sheetId, user.getId()));
    }

    public CheckLikeResponseDTO toggleLike(Long sheetId, CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        return likeMapper
                .isLikeExistToCheckLikeResponseDTO(likeService.toggleLike(sheetId, user.getId()));
    }
}

