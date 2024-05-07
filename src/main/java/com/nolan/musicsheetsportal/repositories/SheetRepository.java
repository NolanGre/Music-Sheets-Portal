package com.nolan.musicsheetsportal.repositories;

import com.nolan.musicsheetsportal.models.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetRepository extends JpaRepository<Sheet, Long> {
    void deleteByAuthorId(long authorId);
}
