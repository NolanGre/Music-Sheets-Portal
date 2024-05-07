package com.nolan.musicsheetsportal.sevices;

import com.nolan.musicsheetsportal.config.MyUserDetails;
import com.nolan.musicsheetsportal.exception.SheetNotFoundException;
import com.nolan.musicsheetsportal.models.MyUser;
import com.nolan.musicsheetsportal.models.Sheet;
import com.nolan.musicsheetsportal.repositories.SheetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheetService {

    private final SheetRepository sheetRepository;

    @Autowired
    public SheetService(SheetRepository sheetRepository) {
        this.sheetRepository = sheetRepository;
    }

    @Transactional
    public void addSheet(Sheet sheet) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MyUserDetails) {
            MyUserDetails currentUser = (MyUserDetails) authentication.getPrincipal();
            sheet.setAuthor(currentUser.getUser());
            sheetRepository.save(sheet);
        } else {
            throw new IllegalStateException("Unable to determine the current authenticated user");
        }
    }

    public List<Sheet> getAllSheets() {
        return sheetRepository.findAll();
    }

    @Transactional
    public void updateSheet(long id, Sheet updatedSheet) {
        if (id != updatedSheet.getId())
            throw new RuntimeException("Different id: " + id);

        Sheet existingSheet = sheetRepository.findById(id)
                .orElseThrow(() -> new SheetNotFoundException("Sheet with id " + id + " not found"));

        existingSheet.setDescription(updatedSheet.getDescription());
        existingSheet.setGenres(updatedSheet.getGenres());
        existingSheet.setTitle(updatedSheet.getTitle());
        existingSheet.setNoteFileUrl(updatedSheet.getNoteFileUrl());

        sheetRepository.save(existingSheet);
    }

    public void removeSheetById(long sheetId) {
        sheetRepository.deleteById(sheetId);
    }

    public boolean isSheetAuthor(long sheetId, MyUser user) {
        return sheetRepository.findById(sheetId).isPresent() &&
                sheetRepository.findById(sheetId).get().getAuthor().equals(user);
    }
}
