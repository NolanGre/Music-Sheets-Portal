package org.example.musicsheets.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.musicsheets.exceptions.FileDeleteException;
import org.example.musicsheets.exceptions.FileUpdateException;
import org.example.musicsheets.exceptions.FileUploadException;
import org.example.musicsheets.exceptions.SheetNotFoundException;
import org.example.musicsheets.models.Sheet;
import org.example.musicsheets.repositories.SheetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class SheetService {

    private final SheetRepository sheetRepository;
    private final UserService userService;
    private final FileService fileService;
    public static final String FOLDER_NAME = "sheets";

    @Transactional
    public Sheet createSheetWithFile(Sheet sheet, MultipartFile file, Long id) {
        sheet.setPublisher(userService.getUserById(id));

        Sheet savedSheet = sheetRepository.save(sheet);

        try {
            savedSheet.setFileUrl(fileService.uploadFile(savedSheet.getId().toString(), FOLDER_NAME, file));
            return sheetRepository.save(savedSheet);
        } catch (Exception e) {
            sheetRepository.deleteById(savedSheet.getId());
            throw new FileUploadException("Failed to upload file for sheet with ID: " + savedSheet.getId() + "; " + e.getMessage());
        }
    }

    public Sheet getSheetById(Long id) {
        return sheetRepository.findById(id)
                .orElseThrow(() -> new SheetNotFoundException("Sheet with ID: " + id + " not found "));
    }

    public List<Sheet> getAllSheets() {
        return sheetRepository.findAll();
    }

    public Page<Sheet> getAllSheetsPaginated(Pageable pageable) {
        return sheetRepository.findAll(pageable);
    }

    @Transactional
    public Sheet updateSheet(Sheet updatedSheet, MultipartFile file, Long sheetId) {
        Sheet existingSheet = getSheetById(sheetId);

        updateSheetDetails(existingSheet, updatedSheet);

        Sheet savedSheet = sheetRepository.save(existingSheet);

        try {
            if (existingSheet.getFileUrl() != null) {
                fileService.deleteFile(existingSheet.getFileUrl());
            }

            savedSheet.setFileUrl(fileService.uploadFile(savedSheet.getId().toString(), FOLDER_NAME, file));
            return sheetRepository.save(savedSheet);
        } catch (Exception e) {
            sheetRepository.deleteById(existingSheet.getId());
            throw new FileUpdateException("Failed to update file for sheet with ID: " + sheetId + "; " + e.getMessage());
        }
    }

    private static void updateSheetDetails(Sheet existingSheet, Sheet updatedSheet) {
        existingSheet.setTitle(updatedSheet.getTitle());
        existingSheet.setAuthor(updatedSheet.getAuthor());
        existingSheet.setDescription(updatedSheet.getDescription());
        existingSheet.setGenre(updatedSheet.getGenre());
    }

    @Transactional
    public void deleteSheet(Long sheetId) {
        Sheet existingSheet = getSheetById(sheetId);
        if (existingSheet == null) {
            throw new SheetNotFoundException("Sheet with ID: " + sheetId + " not found ");
        }

        try {
            if (existingSheet.getFileUrl() != null) {
                fileService.deleteFile(existingSheet.getFileUrl());
            }
        } catch (Exception e) {
            throw new FileDeleteException("Failed to delete file for sheet with ID: " + sheetId + "; " + e.getMessage());

        }
        sheetRepository.deleteById(sheetId);
    }

    public Long getPublisherId(Long sheetId) {
        return getSheetById(sheetId).getPublisher().getId();
    }
}
