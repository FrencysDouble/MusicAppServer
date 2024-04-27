package com.example.MusicAppServer.repositories;

import com.example.MusicAppServer.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track,Long> {
}
