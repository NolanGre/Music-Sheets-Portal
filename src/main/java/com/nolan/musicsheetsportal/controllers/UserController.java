package com.nolan.musicsheetsportal.controllers;

import com.nolan.musicsheetsportal.models.MyUser;
import com.nolan.musicsheetsportal.sevices.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/music-sheet")
public class UserController {

    private final MyUserDetailService service;

    @Autowired
    public UserController(MyUserDetailService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody MyUser user) {
        service.addUser(user);
        return ResponseEntity.ok("User is created");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable long user_id) {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @PutMapping("/users/{user_id}")
    @PreAuthorize("hasAuthority('ADMIN') or @myUserDetailService.hasId(#user_id)")
    public ResponseEntity<?> updateUser(@PathVariable long user_id, @RequestBody MyUser updatedUser) {
        service.updateUser(user_id, updatedUser);
        return ResponseEntity.ok("User is updated");
    }

    @DeleteMapping("/users/{user_id}")
    @PreAuthorize("hasAuthority('ADMIN') or @myUserDetailService.hasId(#user_id)")
    public ResponseEntity<?> deleteUser(@PathVariable long user_id) {
        service.deleteUser(user_id);
        return ResponseEntity.ok("User has been deleted");
    }
}
