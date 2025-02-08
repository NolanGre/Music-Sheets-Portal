package org.example.musicsheets.facades;

import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.sheets.CreateSheetRequestDTO;
import org.example.musicsheets.dto.sheets.UpdateSheetRequestDTO;
import org.example.musicsheets.dto.sheets.WholeSheetResponseDTO;
import org.example.musicsheets.mappers.SheetMapper;
import org.example.musicsheets.models.Sheet;
import org.example.musicsheets.models.User;
import org.example.musicsheets.security.CustomUserDetails;
import org.example.musicsheets.services.AuthorizationService;
import org.example.musicsheets.services.SheetService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "sheets")
public class SheetFacade {

    private final SheetService sheetService;
    private final AuthorizationService authorizationService;
    private final SheetMapper sheetMapper;

    @Cacheable(key = "#sheetId")
    public WholeSheetResponseDTO getWholeSheet(Long sheetId) {
        return sheetMapper.sheetToWholeSheetResponseDTO(sheetService.getSheetById(sheetId));
    }

    public WholeSheetResponseDTO createSheetWithFile(CreateSheetRequestDTO data, MultipartFile file, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        Sheet sheet = sheetService.createSheetWithFile(
                sheetMapper.createSheetRequestDTOtoSheet(data),
                file,
                authenticatedUser.getId());

        return sheetMapper.sheetToWholeSheetResponseDTO(sheet);
    }

    @CachePut(key = "#sheetId")
    public WholeSheetResponseDTO updateWholeSheet(Long sheetId, UpdateSheetRequestDTO data, MultipartFile file, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                sheetService.getPublisherId(sheetId), authenticatedUser.getRole());

        Sheet sheet = sheetService.updateSheet(sheetMapper.updateSheetRequestDTOtoSheet(data),
                file,
                sheetId);

        return sheetMapper.sheetToWholeSheetResponseDTO(sheet);
    }

    @CacheEvict(key = "#sheetId")
    public void deleteSheet(Long sheetId, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                sheetService.getPublisherId(sheetId), authenticatedUser.getRole());

        sheetService.deleteSheet(sheetId);
    }
}
