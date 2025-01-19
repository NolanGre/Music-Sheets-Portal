package org.example.musicsheets.repositories;

import org.example.musicsheets.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllBySheetId(Long sheetId);

    Page<Comment> findAllBySheetId(Long sheetId, Pageable pageable);
}
