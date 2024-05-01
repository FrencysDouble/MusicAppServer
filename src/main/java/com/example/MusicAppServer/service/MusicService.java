package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Track;
import com.example.MusicAppServer.repositories.TrackRepository;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class MusicService {
    private final FileService fileService;

    private final TrackRepository trackRepository;

    public MusicService(FileService fileService, TrackRepository trackRepository) {
        this.fileService = fileService;
        this.trackRepository = trackRepository;
    }

    public Resource streamMusic(Long id) throws FileNotFoundException {

        Track track = trackRepository.getById(id);
        String path = track.getAudioPath();
        File musicFile = fileService.getMusicFile(path);
        if (!musicFile.exists()) {
            throw new FileNotFoundException("Music file not found: " + path);
        }

        return new FileSystemResource(musicFile);
    }
}
