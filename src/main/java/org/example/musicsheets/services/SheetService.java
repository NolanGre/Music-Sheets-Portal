package org.example.musicsheets.services;

import jakarta.transaction.Transactional;
import org.example.musicsheets.exceptions.FileDeleteException;
import org.example.musicsheets.exceptions.FileUpdateException;
import org.example.musicsheets.exceptions.FileUploadException;
import org.example.musicsheets.exceptions.SheetNotFoundException;
import org.example.musicsheets.models.Sheet;
import org.example.musicsheets.repositories.SheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SheetService {

    private final SheetRepository sheetRepository;
    private final UserService userService;
    private final FileService fileService;
    public static final String FOLDER_NAME = "sheets";

    @Autowired
    public SheetService(SheetRepository sheetRepository, @Lazy UserService userService, FileService fileService) {
        this.sheetRepository = sheetRepository;
        this.userService = userService;
        this.fileService = fileService;
    }

    @Transactional
    public Sheet createSheetWithFile(Sheet sheet, MultipartFile file, Long id) {
        sheet.setPublisher(userService.getUserById(id));
        Sheet savedSheet = sheetRepository.save(sheet);

        try {
            savedSheet.setFileUrl(fileService.uploadFile(savedSheet.getId().toString(), FOLDER_NAME, file));
            return sheetRepository.save(savedSheet);
        } catch (Exception e) {
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

        try {
            if (existingSheet.getFileUrl() != null) {
                fileService.deleteFile(existingSheet.getFileUrl());
            }

            existingSheet.setFileUrl(fileService.uploadFile(existingSheet.getId().toString(), FOLDER_NAME, file));
            return sheetRepository.save(existingSheet);
        } catch (Exception e) {
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
        String fileUrl = existingSheet.getFileUrl();

        sheetRepository.deleteById(sheetId);

        if (fileUrl != null) {
            try {
                fileService.deleteFile(fileUrl);
            } catch (Exception e) {
                throw new FileDeleteException("Failed to delete file for sheet with ID: " + sheetId + "; " + e.getMessage());
            }
        }
    }

    public Long getPublisherId(Long sheetId) {
        return getSheetById(sheetId).getPublisher().getId();
    }

    @Transactional
    public void deleteAllSheetsByPublisherId(Long userId) {
        sheetRepository.findAllByPublisherId(userId).forEach(s -> deleteSheet(s.getId()));
    }
}
