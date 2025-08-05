package com.ddg.ArtGallery.controller;

import com.ddg.ArtGallery.model.ArtWork;
import com.ddg.ArtGallery.service.ArtWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArtWorkController {

    @Autowired
    ArtWorkService artWorkService;
    @PostMapping("/artwork")
    public ResponseEntity<ArtWork> addArtWork(@RequestPart ArtWork artwork, @RequestPart MultipartFile imageFile) {
        try {
            ArtWork artWork = artWorkService.addArtWork(artwork, imageFile);
            return new ResponseEntity<>(artWork, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/artworks")
    public ResponseEntity<?> getAllArtWorks() {
        List<ArtWork> artWorks = artWorkService.getAllArtWorks();
        if (!artWorks.isEmpty()) {
            return new ResponseEntity<>(artWorks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Artworks found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/artwork/{id}")
    public ResponseEntity<?> getArtWork(@PathVariable int id) {

        try {
            ArtWork artWork = artWorkService.getArtWork(id);
            return new ResponseEntity<>(artWork, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/artwork/{id}")
    public ResponseEntity<String> deleteArtWork(@PathVariable int id) {
        try {
            artWorkService.deleteArtWork(id);
            return new ResponseEntity<>("Artwork deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/artwork/{id}")
    public ResponseEntity<String> updateArtWork(@PathVariable int id, @RequestPart ArtWork artwork, @RequestPart MultipartFile imageFile) {
        try {
            artWorkService.updateArtWork(id, artwork, imageFile);
            return new ResponseEntity<>("Artwork updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
