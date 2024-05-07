package com.nolan.musicsheetsportal.controllers;

import com.nolan.musicsheetsportal.models.Comment;
import com.nolan.musicsheetsportal.sevices.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/music-sheet")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{sheet_id}")
    public ResponseEntity<?> getCommentsFromSheet(@PathVariable long sheet_id) {
        return ResponseEntity.ok(commentService.getCommentsFromSheet(sheet_id));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/comments/{sheet_id}")
    public ResponseEntity<?> addComment(@RequestBody Comment comment, @PathVariable long sheet_id) {
        commentService.addCommentForSheet(comment, sheet_id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN') or @commentService.isCommentAuthor(#comment_id, authentication.principal.getUser())")
    @PutMapping("/comments/{comment_id}")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment, @PathVariable long comment_id) {
        commentService.updateComment(comment, comment_id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN') or @commentService.isCommentAuthor(#comment_id, authentication.principal.getUser())")
    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable long comment_id) {
        commentService.deleteCommentById(comment_id);
        return ResponseEntity.ok().build();
    }
}
