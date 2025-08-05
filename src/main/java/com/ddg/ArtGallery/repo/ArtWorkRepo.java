package com.ddg.ArtGallery.repo;

import com.ddg.ArtGallery.model.ArtWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtWorkRepo extends JpaRepository<ArtWork, Integer> {
    @Query("SELECT a FROM ArtWork a WHERE a.title LIKE %?1% OR a.artist LIKE %?1% OR a.medium LIKE %?1%")
    List<ArtWork> searchArtWorks(String keyword);
}
