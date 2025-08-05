package com.ddg.ArtGallery.service;

import com.ddg.ArtGallery.model.ArtWork;
import com.ddg.ArtGallery.repo.ArtWorkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    public List<ArtWork> getAllArtWorks() {
        return artWorkRepo.findAll();
    }

    public ArtWork getArtWork(int id) {
        return artWorkRepo.findById(id).orElseThrow(() -> new RuntimeException("Artwork not found for id: " + id));
    }

    public void deleteArtWork(int id) {
        artWorkRepo.findById(id).orElseThrow(() -> new RuntimeException("Artwork not found for id: " + id));
        artWorkRepo.deleteById(id);
    }

    public void updateArtWork(int id, ArtWork artWork, MultipartFile imageFile) throws IOException {
        ArtWork existingArtWork = artWorkRepo.findById(id).orElseThrow(() -> new RuntimeException("Artwork not found for id: " + id));
        existingArtWork.setTitle(artWork.getTitle());
        existingArtWork.setArtist(artWork.getArtist());
        existingArtWork.setMedium(artWork.getMedium());
        existingArtWork.setYear(artWork.getYear());
        if (imageFile != null) {
            existingArtWork.setImageName(imageFile.getOriginalFilename());
            existingArtWork.setImageType(imageFile.getContentType());
            existingArtWork.setImageData(imageFile.getBytes());
        }
        artWorkRepo.save(existingArtWork);
    }

    public List<ArtWork> searchArtWorks(String keyword) {
        List<ArtWork> artWorks = artWorkRepo.searchArtWorks(keyword);
        if (artWorks.isEmpty()) {
            throw new RuntimeException("No Artworks found for keyword: " + keyword);
        }
        return artWorks;
    }
}
