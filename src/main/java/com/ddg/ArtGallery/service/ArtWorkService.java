package com.ddg.ArtGallery.service;

import com.ddg.ArtGallery.model.ArtWork;
import com.ddg.ArtGallery.repo.ArtWorkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ArtWorkService {

    @Autowired
    ArtWorkRepo artWorkRepo;

    public ArtWork addArtWork(ArtWork artwork, MultipartFile imageFile) throws IOException {
        artwork.setImageName(imageFile.getOriginalFilename());
        artwork.setImageType(imageFile.getContentType());
        artwork.setImageData(imageFile.getBytes());
        return artWorkRepo.save(artwork);
    }
}
