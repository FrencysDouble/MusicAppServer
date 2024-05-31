package com.example.MusicAppServer.repositories;

import com.example.MusicAppServer.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {
    List<Playlist> findAll();

    List<Playlist> findByCreatorId(Long id);


}
