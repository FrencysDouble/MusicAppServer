package com.example.MusicAppServer.repositories;

import com.example.MusicAppServer.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Long> {

    List<Album> findAll();

    List<Album> findAlbumsByArtistId(Long artistId);

    List<Album> findByNameContainingIgnoreCase(String name);
    Album findAlbumById(Long id);

}
