package com.ddg.ArtGallery.repo;

import com.ddg.ArtGallery.model.ArtWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtWorkRepo extends JpaRepository<ArtWork, Integer> {
}
