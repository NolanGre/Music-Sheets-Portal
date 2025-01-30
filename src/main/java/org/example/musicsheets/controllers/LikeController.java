package org.example.musicsheets.controllers;

import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.likes.CheckLikeResponseDTO;
import org.example.musicsheets.mappers.LikeMapper;
import org.example.musicsheets.models.User;
import org.example.musicsheets.security.CustomUserDetails;
import org.example.musicsheets.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    @GetMapping("/sheets/{sheetId}/like")
    public ResponseEntity<CheckLikeResponseDTO> checkLike(@PathVariable Long sheetId,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        CheckLikeResponseDTO checkLikeResponseDTO = likeMapper
                .isLikeExistToCheckLikeResponseDTO(likeService.doesLikeExist(sheetId, user.getId()));

        return ResponseEntity.ok(checkLikeResponseDTO);
    }

    @PostMapping("/sheets/{sheetId}/like")
    public ResponseEntity<CheckLikeResponseDTO> toggleLike(@PathVariable Long sheetId,
                                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        CheckLikeResponseDTO checkLikeResponseDTO = likeMapper
                .isLikeExistToCheckLikeResponseDTO(likeService.toggleLike(sheetId, user.getId()));

        return ResponseEntity.ok(checkLikeResponseDTO);
    }
}
