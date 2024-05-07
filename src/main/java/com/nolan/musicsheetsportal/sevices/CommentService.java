package com.nolan.musicsheetsportal.sevices;

import com.nolan.musicsheetsportal.config.MyUserDetails;
import com.nolan.musicsheetsportal.exception.CommentNotFoundException;
import com.nolan.musicsheetsportal.exception.SheetNotFoundException;
import com.nolan.musicsheetsportal.models.Comment;
import com.nolan.musicsheetsportal.models.MyUser;
import com.nolan.musicsheetsportal.repositories.CommentRepository;
import com.nolan.musicsheetsportal.repositories.SheetRepository;
import com.nolan.musicsheetsportal.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final SheetRepository sheetRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, SheetRepository sheetRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.sheetRepository = sheetRepository;
        this.userRepository = userRepository;
    }


    public List<Comment> getCommentsFromSheet(long sheet_id) {
        return commentRepository.findAllBySheetId(sheet_id);
    }

    @Transactional
    public void addCommentForSheet(Comment comment, long sheetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MyUserDetails) {
            MyUserDetails currentUser = (MyUserDetails) authentication.getPrincipal();
            comment.setAuthor(currentUser.getUser());
        } else {
            throw new IllegalStateException("Unable to determine the current authenticated user");
        }

        comment.setSheet(sheetRepository.findById(sheetId)
                .orElseThrow(() -> new SheetNotFoundException("Sheet not found. ID: " + sheetId)));

        commentRepository.save(comment);
    }

    public void deleteCommentById(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void updateComment(Comment updatedComment, long commentId) {
        if (updatedComment.getId() != commentId)
            throw new IllegalArgumentException("Comment id mismatch");

        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found. ID: " + commentId));

        existingComment.setText(updatedComment.getText());

        commentRepository.save(existingComment);
    }

    public boolean isCommentAuthor(long commentId, MyUser user) {
        return commentRepository.findById(commentId).isPresent() &&
                commentRepository.findById(commentId).get().getAuthor().equals(user);
    }

}
