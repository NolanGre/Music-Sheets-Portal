package com.nolan.musicsheetsportal.controllers;

import com.nolan.musicsheetsportal.sevices.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/music-sheet")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/likes/{sheet_id}")
    public ResponseEntity<?> checkLike(@PathVariable long sheet_id) {
        return ResponseEntity.ok(likeService.doesLikeExist(sheet_id));
    }

    @GetMapping("/likes/count/{sheet_id}")
    public ResponseEntity<?> getNumberOfLikes(@PathVariable long sheet_id) {
        return ResponseEntity.ok(likeService.getNumberOfLikes(sheet_id));
    }

    @PostMapping("/likes/{sheet_id}")
    public ResponseEntity<?> toggleLike(@PathVariable long sheet_id) {
        likeService.toggleLike(sheet_id);
        return ResponseEntity.ok().build();
    }


}
