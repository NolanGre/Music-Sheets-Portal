package com.nolan.musicsheetsportal.repositories;

import com.nolan.musicsheetsportal.models.Like;
import com.nolan.musicsheetsportal.models.LikeId;
import com.nolan.musicsheetsportal.models.MyUser;
import com.nolan.musicsheetsportal.models.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepository extends JpaRepository<Like, LikeId> {

    void deleteAllByMyUser(MyUser user);

    void deleteBySheetAndMyUser(Sheet sheet, MyUser myUser);

    boolean existsBySheetAndMyUser(Sheet sheet, MyUser myUser);

    int countBySheet(Sheet sheet);
}
