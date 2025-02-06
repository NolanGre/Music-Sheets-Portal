package org.example.musicsheets.facades;

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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "comments")
public class CommentFacade {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final AuthorizationService authorizationService;

    @Cacheable(key = "#commentId")
    public CommentResponseDTO getCommentByCommentId(Long commentId) {
        return commentMapper.commentToCommentResponseDTO(commentService.getCommentById(commentId));
    }

    public List<CommentResponseDTO> getAllCommentsBySheetId(Long sheetId) {
        return commentMapper.commentsListToCommentResponseDTOsList(commentService.getAllCommentsBySheetId(sheetId));
    }

    public CommentResponseDTO createComment(Long sheetId, CreateCommentRequestDTO createCommentRequestDTO, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        Comment comment = commentService.createComment(sheetId, authenticatedUser.getId(), createCommentRequestDTO.text());

        return commentMapper.commentToCommentResponseDTO(comment);
    }

    @CachePut(key = "#commentId")
    public CommentResponseDTO updateCommentById(Long commentId, UpdateCommentRequestDTO updateCommentRequestDTO, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                commentService.getOwnerId(commentId), authenticatedUser.getRole());

        Comment comment = commentService.updateWholeComment(commentId, updateCommentRequestDTO.text());

        return commentMapper.commentToCommentResponseDTO(comment);
    }

    @CacheEvict(key = "#commentId")
    public void deleteCommentById(Long commentId, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                commentService.getOwnerId(commentId), authenticatedUser.getRole());

        commentService.deleteCommentById(commentId);
    }
}
