package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/sheets")
@AllArgsConstructor
public class SheetController {

    private final SheetService sheetService;
    private final AuthorizationService authorizationService;
    private final SheetMapper sheetMapper;

    @GetMapping("/{sheetId}")
    public ResponseEntity<WholeSheetResponseDTO> getWholeSheet(@PathVariable Long sheetId) {

        Sheet sheet = sheetService.getSheetById(sheetId);

        return ResponseEntity.ok(sheetMapper.sheetToWholeSheetResponseDTO(sheet));
    }

    @PostMapping("")
    public ResponseEntity<WholeSheetResponseDTO> createSheet(@Valid @RequestBody CreateSheetRequestDTO createSheetRequestDTO,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        Sheet sheet = sheetService.createSheet(
                sheetMapper.createSheetRequestDTOtoSheet(createSheetRequestDTO),
                authenticatedUser.getId());
        URI location = URI.create("/api/v1/sheets/" + sheet.getId());

        return ResponseEntity
                .created(location)
                .body(sheetMapper.sheetToWholeSheetResponseDTO(sheet));
    }

    //TODO change response dto?
    @PutMapping("/{sheetId}")
    public ResponseEntity<WholeSheetResponseDTO> updateWholeSheet(@PathVariable Long sheetId,
                                              @Valid @RequestBody UpdateSheetRequestDTO updateSheetRequestDTO,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        User authenticatedUser = userDetails.getUser();

        if (!authorizationService.isAdminOrOwner(authenticatedUser.getId(), sheetId, authenticatedUser.getRole())) {
            throw new AccessDeniedException("You do not have permission to update this sheet");
        }

        Sheet sheet = sheetService.updateSheet(
                sheetMapper.updateSheetRequestDTOtoSheet(updateSheetRequestDTO),
                sheetId);

        return ResponseEntity.ok(sheetMapper.sheetToWholeSheetResponseDTO(sheet));
    }

    @DeleteMapping("/{sheetId}")
    public ResponseEntity<?> deleteSheet(@PathVariable Long sheetId, @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        User authenticatedUser = userDetails.getUser();

        if (!authorizationService.isAdminOrOwner(authenticatedUser.getId(), sheetId, authenticatedUser.getRole())) {
            throw new AccessDeniedException("You do not have permission to update this sheet");
        }

        sheetService.deleteSheet(sheetId);

        return ResponseEntity.noContent().build();
    }
}
