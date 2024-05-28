package com.example.MusicAppServer.repositories;

import com.example.MusicAppServer.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist,Long> {

    List<Artist> findAll();


    List<Artist> findByNameContainingIgnoreCase(String name);

}
