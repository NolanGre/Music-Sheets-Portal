package com.nolan.musicsheetsportal.repositories;

import com.nolan.musicsheetsportal.models.Comment;
import com.nolan.musicsheetsportal.models.MyUser;
import com.nolan.musicsheetsportal.models.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAllByAuthor(MyUser author);

    List<Comment> findAllBySheetId(long sheet_id);
}
