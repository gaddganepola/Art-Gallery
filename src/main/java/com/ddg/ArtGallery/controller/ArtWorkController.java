package com.ddg.ArtGallery.controller;

import com.ddg.ArtGallery.model.ArtWork;
import com.ddg.ArtGallery.service.ArtWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

}
