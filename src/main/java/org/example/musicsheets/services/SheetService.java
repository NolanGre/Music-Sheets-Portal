package org.example.musicsheets.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.musicsheets.exceptions.SheetNotFoundException;
import org.example.musicsheets.models.Sheet;
import org.example.musicsheets.repositories.SheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SheetService {

    private final SheetRepository sheetRepository;
    private final UserService userService;

    @Transactional
    public Sheet createSheet(Sheet sheet, Long id) {
        sheet.setPublisher(userService.getUserById(id));
        return sheetRepository.save(sheet);
    }

    public Sheet getSheetById(Long id) {
        return sheetRepository.findById(id)
                .orElseThrow(() -> new SheetNotFoundException("Sheet with ID: " + id + "not found "));
    }

    public List<Sheet> getAllSheets() {
        return sheetRepository.findAll();
    }

    public Page<Sheet> getAllSheetsPaginated(Pageable pageable) {
        return sheetRepository.findAll(pageable);
    }

    @Transactional
    public Sheet updateSheet(Sheet updatedSheet, Long id) {
        Sheet existingSheet = getSheetById(id);

        updateSheetDetails(existingSheet, updatedSheet);

        return sheetRepository.save(existingSheet);
    }

    private static void updateSheetDetails(Sheet existingSheet, Sheet updatedSheet) {
        existingSheet.setTitle(updatedSheet.getTitle());
        existingSheet.setAuthor(updatedSheet.getAuthor());
        existingSheet.setDescription(updatedSheet.getDescription());
        existingSheet.setGenre(updatedSheet.getGenre());
        existingSheet.setFileUrl(updatedSheet.getFileUrl());
    }

    @Transactional
    public void deleteSheet(Long id) {
        if (!sheetRepository.existsById(id)) {
            throw new SheetNotFoundException("Sheet with ID: " + id + " not found ");
        }

        sheetRepository.deleteById(id);
    }

    public Long getPublisherId(Long sheetId) {
        return getSheetById(sheetId).getPublisher().getId();
    }
}
