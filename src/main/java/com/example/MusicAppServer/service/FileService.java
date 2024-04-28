package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Album;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is main Service to work with files such us: Music, Image
 */
@Service
public class FileService {

    public static final String uploadDir = "artists/";

    public static final String imageArtistFileName = "artistLogo";
    public static final String imageAlbumFileName = "albumLogo";

    private final MultipartFile image;

    private final Long artistId;

    private final List<MultipartFile> audioFiles;

    private final Long albumId;

    private final List<String> albumTrackNames;


    FileService()
    {
        this.image = null;
        this.artistId = null;
        this.audioFiles = null;
        this.albumId = null;
        this.albumTrackNames = null;
    }

    FileService(MultipartFile image, Long artistId)
    {
        this.image = image;
        this.artistId = artistId;
        this.albumTrackNames = null;
        audioFiles = null;
        albumId = null;
    }

    FileService(MultipartFile albumImage, Long artistId, List<MultipartFile> audioFiles, Album album)
    {
        this.image = albumImage;
        this.artistId = artistId;
        this.audioFiles = audioFiles;
        this.albumId = album.getId();
        this.albumTrackNames = album.getTrackNames();
    }

    public String saveArtistImage() throws IOException {
        String artistDir = uploadDir + artistId + "/";
        return saveImageFile(image, artistDir);
    }

    public String saveAlbumImage() throws IOException {
        String albumDir = uploadDir + artistId + "/Albums/" + albumId + "/";
        return saveImageFile(image,albumDir);
    }

    public List<String> saveAlbumAudioFiles() throws IOException {
        String artistDir = uploadDir + artistId + "/Albums/" + albumId + "/Tracks/";
        List<String> audioFilesPath = new ArrayList<>();
        for (int i = 0; i < audioFiles.size(); i++) {
            audioFilesPath.add(saveAudioFile(audioFiles.get(i), artistDir, albumTrackNames.get(i)));
        }
        return audioFilesPath;
    }

    private String saveImageFile(MultipartFile file, String directory) throws IOException {
        Path uploadPath = Paths.get(directory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        if (directory.contains("/Albums/"))
        {
            Path filePath = uploadPath.resolve(imageAlbumFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        }

        Path filePath = uploadPath.resolve(imageArtistFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    private String saveAudioFile(MultipartFile file,String directory,String trackName) throws IOException {

        Path uploadPath = Paths.get(directory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(trackName+".mp3");
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }










    /*public void saveMusicFile(MultipartFile file) throws IOException {
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


    public String saveImageFile(MultipartFile image,Long artistId) throws IOException {
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
    public void saveArtistImage(MultipartFile image, Long artistId) throws IOException {
        Path uploadPath = Paths.get(uploadDir + artistId + "/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(image.getOriginalFilename());
        Files.copy(image.getInputStream(), filePath);
    }

    public void saveAlbumAudioFiles(List<MultipartFile> audioFiles, Long artistId, Long albumId) throws IOException {
        Path uploadPath = Paths.get(uploadDir + artistId + "/" + albumId + "/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (MultipartFile audioFile : audioFiles) {
            Path filePath = uploadPath.resolve(audioFile.getOriginalFilename());
            Files.copy(audioFile.getInputStream(), filePath);
        }
    }*/

}
