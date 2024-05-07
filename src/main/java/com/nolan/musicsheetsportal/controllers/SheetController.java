package com.nolan.musicsheetsportal.controllers;

import com.nolan.musicsheetsportal.models.Sheet;
import com.nolan.musicsheetsportal.sevices.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/music-sheet")
public class SheetController {

    private final SheetService service;

    @Autowired
    public SheetController(SheetService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/sheets")
    public ResponseEntity<?> addSheet(@RequestBody Sheet sheet) {
        service.addSheet(sheet);
        return ResponseEntity.ok("Sheet added successfully");
    }

    @GetMapping("/sheets")
    public ResponseEntity<?> getAllSheets() {
        return ResponseEntity.ok(service.getAllSheets());
    }

    @PreAuthorize("hasAuthority('ADMIN') or @sheetService.isSheetAuthor(#sheet_id, authentication.principal.getUser())")
    @PutMapping("/sheets/{sheet_id}")
    public ResponseEntity<?> updateSheet(@PathVariable int sheet_id, @RequestBody Sheet sheet) {
        service.updateSheet(sheet_id, sheet);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN') or @sheetService.isSheetAuthor(#sheet_id, authentication.principal.getUser())")
    @DeleteMapping("/sheets/{sheet_id}")
    public ResponseEntity<?> deleteSheet(@PathVariable long sheet_id) {
        service.removeSheetById(sheet_id);
        return ResponseEntity.ok("Sheet deleted successfully");
    }

}
