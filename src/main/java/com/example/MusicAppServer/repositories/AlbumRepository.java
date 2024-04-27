package com.example.MusicAppServer.repositories;

import com.example.MusicAppServer.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album,Long> {

}
