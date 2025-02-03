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

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SheetController {

    private final SheetService sheetService;
    private final AuthorizationService authorizationService;
    private final SheetMapper sheetMapper;

    @GetMapping("/sheets/{sheetId}")
    public ResponseEntity<WholeSheetResponseDTO> getWholeSheet(@PathVariable Long sheetId) {
        Sheet sheet = sheetService.getSheetById(sheetId);

        return ResponseEntity.ok(sheetMapper.sheetToWholeSheetResponseDTO(sheet));
    }

    @PostMapping("/sheets")
    public ResponseEntity<WholeSheetResponseDTO> createSheet(@Valid @RequestBody CreateSheetRequestDTO createSheetRequestDTO,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        Sheet sheet = sheetService.createSheet(
                sheetMapper.createSheetRequestDTOtoSheet(createSheetRequestDTO),
                authenticatedUser.getId());
        URI location = URI.create("/api/v1/sheets/" + sheet.getId());

        return ResponseEntity.created(location).body(sheetMapper.sheetToWholeSheetResponseDTO(sheet));
    }

    @PutMapping("/sheets/{sheetId}")
    public ResponseEntity<WholeSheetResponseDTO> updateWholeSheet(@PathVariable Long sheetId,
                                                                  @Valid @RequestBody UpdateSheetRequestDTO updateSheetRequestDTO,
                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                sheetService.getPublisherId(sheetId), authenticatedUser.getRole());

        Sheet sheet = sheetService.updateSheet(sheetMapper.updateSheetRequestDTOtoSheet(updateSheetRequestDTO),
                sheetId);

        return ResponseEntity.ok(sheetMapper.sheetToWholeSheetResponseDTO(sheet));
    }

    @DeleteMapping("sheets/{sheetId}")
    public ResponseEntity<?> deleteSheet(@PathVariable Long sheetId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User authenticatedUser = userDetails.getUser();

        authorizationService.checkAdminOrOwner(authenticatedUser.getId(),
                sheetService.getPublisherId(sheetId), authenticatedUser.getRole());

        sheetService.deleteSheet(sheetId);

        return ResponseEntity.noContent().build();
    }
}
