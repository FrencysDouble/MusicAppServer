package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Track;
import com.example.MusicAppServer.repositories.TrackRepository;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.file.Files;

@Service
public class MusicService {
    private final FileService fileService;

    private final TrackRepository trackRepository;

    public MusicService(FileService fileService, TrackRepository trackRepository) {
        this.fileService = fileService;
        this.trackRepository = trackRepository;
    }

    public File streamMusic(Long id) throws FileNotFoundException {
        Track track = trackRepository.getById(id);
        String path = track.getAudioPath();
        File musicFile = fileService.getFile(path);
        if (!musicFile.exists()) {
            throw new FileNotFoundException("Music file not found: " + path);
        }
        return musicFile;
    }
}
