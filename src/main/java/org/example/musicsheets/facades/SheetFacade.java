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
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SheetFacade {

    private final SheetService sheetService;
    private final AuthorizationService authorizationService;
    private final SheetMapper sheetMapper;

    public WholeSheetResponseDTO getWholeSheet(Long sheetId) {
        return sheetMapper.sheetToWholeSheetResponseDTO(sheetService.getSheetById(sheetId));
    }

    public WholeSheetResponseDTO createSheet(CreateSheetRequestDTO createSheetRequestDTO, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        Sheet sheet = sheetService.createSheet(
                sheetMapper.createSheetRequestDTOtoSheet(createSheetRequestDTO),
                authenticatedUser.getId());

        return sheetMapper.sheetToWholeSheetResponseDTO(sheet);
    }

    public WholeSheetResponseDTO updateWholeSheet(Long sheetId, UpdateSheetRequestDTO updateSheetRequestDTO, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                sheetService.getPublisherId(sheetId), authenticatedUser.getRole());

        Sheet sheet = sheetService.updateSheet(sheetMapper.updateSheetRequestDTOtoSheet(updateSheetRequestDTO),
                sheetId);

        return sheetMapper.sheetToWholeSheetResponseDTO(sheet);
    }

    public void deleteSheet(Long sheetId, CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                sheetService.getPublisherId(sheetId), authenticatedUser.getRole());

        sheetService.deleteSheet(sheetId);
    }
}
