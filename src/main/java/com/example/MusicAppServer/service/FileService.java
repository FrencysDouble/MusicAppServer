package com.example.MusicAppServer.service;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * This is main Service to work with files such us: Music, Image
 */
@Service
public class FileService {

    private static final String musicUploadDir = "music/files/";
    public static final String imageUploadDir = "image/files/";
    public void saveMusicFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(musicUploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        Files.copy(file.getInputStream(), filePath);
    }

    public File getMusicFile(String filename) throws FileNotFoundException {
        Path filePath = Paths.get(musicUploadDir + filename);
        File musicFile = filePath.toFile();

        if (!musicFile.exists()) {
            throw new FileNotFoundException("Music file not found: " + filename);
        }

        return musicFile;
    }


    public String saveImageFile(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(imageUploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFilename = image.getOriginalFilename();
        String filename = StringUtils.cleanPath(Objects.requireNonNull(originalFilename));
        String imagePath = imageUploadDir + filename;
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to save image file: " + filename, e);
        }
        return imagePath;
    }

    public File getImageFile(String filename) throws FileNotFoundException
    {
        Path filePath = Paths.get(imageUploadDir + filename);
        File imageFile = filePath.toFile();

        if (!imageFile.exists()) {
            throw new FileNotFoundException("Image file not found: " + filename);
        }

        return imageFile;
    }
}
