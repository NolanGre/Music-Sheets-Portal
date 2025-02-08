package org.example.musicsheets.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.musicsheets.dto.sheets.CreateSheetRequestDTO;
import org.example.musicsheets.dto.sheets.UpdateSheetRequestDTO;
import org.example.musicsheets.dto.sheets.WholeSheetResponseDTO;
import org.example.musicsheets.facades.SheetFacade;
import org.example.musicsheets.security.CustomUserDetails;
import org.example.musicsheets.validation.ValidFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/sheets", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WholeSheetResponseDTO> createSheetWithFile(@Valid @RequestPart("data") CreateSheetRequestDTO data,
                                                                     @Valid @ValidFile @RequestPart("file") MultipartFile file,
                                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        WholeSheetResponseDTO responseDTO = sheetFacade.createSheetWithFile(data, file, userDetails);
        URI location = URI.create("/api/v1/sheets/" + responseDTO.id());

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/sheets/{sheetId}")
    public ResponseEntity<WholeSheetResponseDTO> updateWholeSheet(@PathVariable Long sheetId,
                                                                  @Valid @RequestPart("data") UpdateSheetRequestDTO data,
                                                                  @Valid @ValidFile @RequestPart("file") MultipartFile file,
                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(sheetFacade.updateWholeSheet(sheetId, data, file, userDetails));
    }

    @DeleteMapping("sheets/{sheetId}")
    public ResponseEntity<Void> deleteSheet(@PathVariable Long sheetId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        sheetFacade.deleteSheet(sheetId, userDetails);
        return ResponseEntity.noContent().build();
    }
}
