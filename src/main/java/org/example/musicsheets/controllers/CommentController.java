package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.comments.CommentResponseDTO;
import org.example.musicsheets.dto.comments.CreateCommentRequestDTO;
import org.example.musicsheets.dto.comments.UpdateCommentRequestDTO;
import org.example.musicsheets.mappers.CommentMapper;
import org.example.musicsheets.models.Comment;
import org.example.musicsheets.models.User;
import org.example.musicsheets.security.CustomUserDetails;
import org.example.musicsheets.services.AuthorizationService;
import org.example.musicsheets.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final AuthorizationService authorizationService;

    @GetMapping("/sheets/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> getCommentByCommentId(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);

        return ResponseEntity.ok(commentMapper.commentToCommentResponseDTO(comment));
    }

    @GetMapping("/sheets/{sheetId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentsBySheetId(@PathVariable Long sheetId) {
        List<Comment> comments = commentService.getAllCommentsBySheetId(sheetId);

        return ResponseEntity.ok(commentMapper.commentsListToCommentResponseDTOsList(comments));
    }

    @PostMapping("/sheets/{sheetId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long sheetId,
                                                            @Valid @RequestBody CreateCommentRequestDTO createCommentRequestDTO,
                                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User authenticatedUser = customUserDetails.getUser();

        Comment comment = commentService.createComment(sheetId, authenticatedUser.getId(), createCommentRequestDTO.text());
        URI location = URI.create("/api/v1/sheets/comments/" + comment.getId());

        return ResponseEntity.created(location).body(commentMapper.commentToCommentResponseDTO(comment));
    }

    @PutMapping("/sheets/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateCommentById(@PathVariable Long commentId,
                                                                @Valid @RequestBody UpdateCommentRequestDTO updateCommentRequestDTO,
                                                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User authenticatedUser = customUserDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                commentService.getOwnerId(commentId), authenticatedUser.getRole());

        Comment comment = commentService.updateWholeComment(commentId, updateCommentRequestDTO.text());

        return ResponseEntity.ok(commentMapper.commentToCommentResponseDTO(comment));
    }

    @DeleteMapping("/sheets/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long commentId,
                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User authenticatedUser = customUserDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                commentService.getOwnerId(commentId), authenticatedUser.getRole());

        commentService.deleteCommentById(commentId);

        return ResponseEntity.noContent().build();
    }
}
