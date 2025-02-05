package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.comments.CommentResponseDTO;
import org.example.musicsheets.dto.comments.CreateCommentRequestDTO;
import org.example.musicsheets.dto.comments.UpdateCommentRequestDTO;
import org.example.musicsheets.facades.CommentFacade;
import org.example.musicsheets.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;

    @GetMapping("/sheets/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> getCommentByCommentId(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentFacade.getCommentByCommentId(commentId));
    }

    @GetMapping("/sheets/{sheetId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentsBySheetId(@PathVariable Long sheetId) {
        return ResponseEntity.ok(commentFacade.getAllCommentsBySheetId(sheetId));
    }

    @PostMapping("/sheets/{sheetId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long sheetId,
                                                            @Valid @RequestBody CreateCommentRequestDTO createCommentRequestDTO,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        CommentResponseDTO responseDTO = commentFacade.createComment(sheetId, createCommentRequestDTO, userDetails);
        URI location = URI.create("/api/v1/sheets/comments/" + responseDTO.id());

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/sheets/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateCommentById(@PathVariable Long commentId,
                                                                @Valid @RequestBody UpdateCommentRequestDTO updateCommentRequestDTO,
                                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(commentFacade.updateCommentById(commentId, updateCommentRequestDTO, userDetails));
    }

    @DeleteMapping("/sheets/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long commentId,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentFacade.deleteCommentById(commentId, userDetails);
        return ResponseEntity.noContent().build();
    }
}
