package org.example.musicsheets.repositories;

import org.example.musicsheets.models.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SheetRepository extends JpaRepository<Sheet, Long> {
    List<SheetIdOnly> findAllByPublisherId(Long publisherId);

    interface SheetIdOnly {
        Long getId();
    }
}
