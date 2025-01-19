package org.example.musicsheets.repositories;

import org.example.musicsheets.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Like.LikeId> {

    boolean existsBySheetIdAndUserId(Long sheetId, Long userId);
    int countBySheetId(Long sheetId);
    void deleteBySheetIdAndUserId(Long sheetId, Long userId);
}
