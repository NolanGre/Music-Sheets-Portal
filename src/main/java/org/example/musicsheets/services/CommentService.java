package org.example.musicsheets.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.musicsheets.exceptions.CommentNotFoundException;
import org.example.musicsheets.models.Comment;
import org.example.musicsheets.models.Sheet;
import org.example.musicsheets.models.User;
import org.example.musicsheets.repositories.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final SheetService sheetService;
    private final UserService userService;

    @Transactional
    public Comment createComment(Long sheetId, Long authorId, String text) {
        Sheet sheet = sheetService.getSheetById(sheetId);
        User author = userService.getUserById(authorId);

        return commentRepository.save(new Comment(sheet, author, text));
    }

    public Comment getCommentById(Long commentId) throws CommentNotFoundException {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment with ID: " + commentId + " not found"));
    }

    public List<Comment> getAllCommentsBySheetId(Long sheetId) {  //sheetId
        return commentRepository.findAllBySheetId(sheetId);
    }

    public Page<Comment> getAllCommentsBySheetIdPaginated(Long sheetId, Pageable pageable) {
        return commentRepository.findAllBySheetId(sheetId, pageable);
    }

    @Transactional
    public Comment updateWholeComment(Long commentId, String updatedText) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment with ID: " + commentId + " not found"));

        existingComment.setText(updatedText);

        return commentRepository.save(existingComment);
    }

    @Transactional
    public void deleteCommentById(Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment with ID: " + commentId + " not found"));

        commentRepository.deleteById(commentId);
    }

    public Long getOwnerId(Long commentId) {
        return getCommentById(commentId).getAuthor().getId();
    }
}
