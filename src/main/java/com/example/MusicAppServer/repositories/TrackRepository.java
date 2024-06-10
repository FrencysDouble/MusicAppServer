package com.example.MusicAppServer.repositories;

import com.example.MusicAppServer.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track,Long> {

    Optional<Track> findById(Long id);

    List<Track> findByNameContainingIgnoreCase(String name);
}
