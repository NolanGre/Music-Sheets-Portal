package com.nolan.musicsheetsportal.sevices;

import com.nolan.musicsheetsportal.config.MyUserDetails;
import com.nolan.musicsheetsportal.exception.SheetNotFoundException;
import com.nolan.musicsheetsportal.models.Like;
import com.nolan.musicsheetsportal.models.MyUser;
import com.nolan.musicsheetsportal.models.Sheet;
import com.nolan.musicsheetsportal.repositories.LikeRepository;
import com.nolan.musicsheetsportal.repositories.SheetRepository;
import com.nolan.musicsheetsportal.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final SheetRepository sheetRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, SheetRepository sheetRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.sheetRepository = sheetRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean doesLikeExist(long sheetId) {
        MyUser user = getCurrentUser();
        Sheet sheet = getSheetById(sheetId);

        return likeRepository.existsBySheetAndMyUser(sheet, user);
    }

    @Transactional
    public void toggleLike(long sheetId) {

        MyUser user = getCurrentUser();
        Sheet sheet = getSheetById(sheetId);

        if (likeRepository.existsBySheetAndMyUser(sheet, user)) {
            likeRepository.deleteBySheetAndMyUser(sheet, user);
        } else {
            likeRepository.save(new Like(sheet, user));
        }
    }

    private MyUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof MyUserDetails)) {
            throw new IllegalStateException("User is not authenticated");
        }

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + getCurrentUser().getUsername()));
    }

    private Sheet getSheetById(long sheetId) {
        return sheetRepository.findById(sheetId)
                .orElseThrow(() -> new SheetNotFoundException("Sheet not found. ID: " + sheetId));
    }

    public int getNumberOfLikes(long sheetId) {
        return likeRepository.countBySheet(getSheetById(sheetId));
    }
}
