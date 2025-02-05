package org.example.musicsheets.controllers;

import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.likes.CheckLikeResponseDTO;
import org.example.musicsheets.facades.LikeFacade;
import org.example.musicsheets.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class LikeController {

    private final LikeFacade likeFacade;

    @GetMapping("/sheets/{sheetId}/like")
    public ResponseEntity<CheckLikeResponseDTO> checkLike(@PathVariable Long sheetId,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(likeFacade.checkLike(sheetId, userDetails));
    }

    @PostMapping("/sheets/{sheetId}/like")
    public ResponseEntity<CheckLikeResponseDTO> toggleLike(@PathVariable Long sheetId,
                                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(likeFacade.toggleLike(sheetId, userDetails));
    }
}
