package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.sheets.CreateSheetRequestDTO;
import org.example.musicsheets.dto.sheets.UpdateSheetRequestDTO;
import org.example.musicsheets.dto.sheets.WholeSheetResponseDTO;
import org.example.musicsheets.facades.SheetFacade;
import org.example.musicsheets.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SheetController {

    private final SheetFacade sheetFacade;

    @GetMapping("/sheets/{sheetId}")
    public ResponseEntity<WholeSheetResponseDTO> getWholeSheet(@PathVariable Long sheetId) {
        return ResponseEntity.ok(sheetFacade.getWholeSheet(sheetId));
    }

    @PostMapping("/sheets")
    public ResponseEntity<WholeSheetResponseDTO> createSheet(@Valid @RequestBody CreateSheetRequestDTO createSheetRequestDTO,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        WholeSheetResponseDTO responseDTO = sheetFacade.createSheet(createSheetRequestDTO, userDetails);
        URI location = URI.create("/api/v1/sheets/" + responseDTO.id());

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/sheets/{sheetId}")
    public ResponseEntity<WholeSheetResponseDTO> updateWholeSheet(@PathVariable Long sheetId,
                                                                  @Valid @RequestBody UpdateSheetRequestDTO updateSheetRequestDTO,
                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(sheetFacade.updateWholeSheet(sheetId, updateSheetRequestDTO, userDetails));
    }

    @DeleteMapping("sheets/{sheetId}")
    public ResponseEntity<Void> deleteSheet(@PathVariable Long sheetId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        sheetFacade.deleteSheet(sheetId, userDetails);
        return ResponseEntity.noContent().build();
    }
}
