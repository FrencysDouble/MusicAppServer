package com.example.MusicAppServer.repositories;

import com.example.MusicAppServer.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track,Long> {

    Track getById(Long id);

    List<Track> findByNameContainingIgnoreCase(String name);
}
