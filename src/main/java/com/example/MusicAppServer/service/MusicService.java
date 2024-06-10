package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Album;
import com.example.MusicAppServer.model.Track;
import com.example.MusicAppServer.repositories.TrackRepository;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
        System.out.println(musicFile);
        return musicFile;
    }

    public Resource getImage(String path) throws FileNotFoundException {
        try {
            File file = fileService.getFile(path);
            Path filePath = file.toPath().toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + path);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error in URL formation for file: " + path, e);
        }
    }

}
