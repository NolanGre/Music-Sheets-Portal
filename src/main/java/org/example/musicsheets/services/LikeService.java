package org.example.musicsheets.services;

import jakarta.transaction.Transactional;
import org.example.musicsheets.models.Like;
import org.example.musicsheets.models.Sheet;
import org.example.musicsheets.models.User;
import org.example.musicsheets.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final SheetService sheetService;
    private final UserService userService;

    @Autowired
    public LikeService(LikeRepository likeRepository, SheetService sheetService, UserService userService) {
        this.likeRepository = likeRepository;
        this.sheetService = sheetService;
        this.userService = userService;
    }

    public boolean doesLikeExist(Long sheetId, Long userId) {
        return likeRepository.existsBySheetIdAndUserId(sheetId, userId);
    }

    @Transactional
    public boolean toggleLike(Long sheetId, Long userId) {
        Sheet sheet = sheetService.getSheetById(sheetId);
        User user = userService.getUserById(userId);

        if (doesLikeExist(sheetId, userId)) {
            likeRepository.deleteBySheetIdAndUserId(sheetId, userId);
            return false;
        } else {
            likeRepository.save(new Like(sheet, user));
            return true;
        }
    }

    public int countLikesBySheetId(Long sheetId) {
        return likeRepository.countBySheetId(sheetId);
    }
}
