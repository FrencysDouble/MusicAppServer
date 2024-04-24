package com.example.MusicAppServer.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileService {

    private static final String uploadDir = "music/files/";
    public void saveMusicFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        Files.copy(file.getInputStream(), filePath);
    }

    public File getMusicFile(String filename) throws FileNotFoundException {
        Path filePath = Paths.get(uploadDir + filename);
        File musicFile = filePath.toFile();

        if (!musicFile.exists()) {
            throw new FileNotFoundException("Music file not found: " + filename);
        }

        return musicFile;
    }
}
