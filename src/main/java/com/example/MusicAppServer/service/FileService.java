package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Album;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;

/**
 * This is main Service to work with files such us: Music, Image
 */
@Service
public class FileService {

    public static final String uploadDir = "artists" + File.separator;

    public static final String uploadPlaylistDir = "user" + File.separator;
    public static final String imageArtistFileName = "artistLogo";
    public static final String imageAlbumFileName = "albumLogo";

    private final MultipartFile image;

    private final Long artistId;

    private final List<MultipartFile> audioFiles;

    private final Long albumId;

    private final List<String> albumTrackNames;

    FileService() {
        this.image = null;
        this.artistId = null;
        this.audioFiles = null;
        this.albumId = null;
        this.albumTrackNames = null;
    }

    FileService(MultipartFile image, Long artistId) {
        this.image = image;
        this.artistId = artistId;
        this.albumTrackNames = null;
        this.audioFiles = null;
        this.albumId = null;
    }

    FileService(MultipartFile albumImage, Long artistId, List<MultipartFile> audioFiles, Album album) {
        this.image = albumImage;
        this.artistId = artistId;
        this.audioFiles = audioFiles;
        this.albumId = album.getId();
        this.albumTrackNames = album.getTrackNames();
    }

    public String saveArtistImage() throws IOException {
        String artistDir = uploadDir + artistId + File.separator;
        return saveImageFile(image, artistDir);
    }

    public String saveAlbumImage() throws IOException {
        String albumDir = uploadDir + artistId + File.separator + "Albums" + File.separator + albumId + File.separator;
        return saveImageFile(image, albumDir);
    }

    public List<String> saveAlbumAudioFiles() throws IOException {
        String artistDir = uploadDir + artistId + File.separator + "Albums" + File.separator + albumId + File.separator + "Tracks" + File.separator;
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

        if (directory.contains(File.separator + "Albums" + File.separator)) {
            Path filePath = uploadPath.resolve(imageAlbumFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        }

        Path filePath = uploadPath.resolve(imageArtistFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    private String saveAudioFile(MultipartFile file, String directory, String trackName) throws IOException {
        Path uploadPath = Paths.get(directory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(trackName + ".mp3");
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    public File getFile(String path) throws FileNotFoundException {
        Path filePath = Paths.get(path);
        File musicFile = filePath.toFile();

        if (!musicFile.exists()) {
            throw new FileNotFoundException("Music file not found: " + path);
        }

        return musicFile;
    }
    public String generatePlaylistImage(List<String> trackImagePaths, String playlistId, String userId) throws IOException, NoSuchAlgorithmException {
        if (trackImagePaths.isEmpty()) {
            throw new IllegalArgumentException("At least 1 image is required to generate the playlist image");
        }

        // Уникализируем изображения
        List<String> uniqueTrackImagePaths = getUniqueImagePaths(trackImagePaths);

        BufferedImage combined;
        if (uniqueTrackImagePaths.size() < 4) {
            combined = ImageIO.read(new File(uniqueTrackImagePaths.get(0)));
        } else {
            BufferedImage image1 = ImageIO.read(new File(uniqueTrackImagePaths.get(0)));
            BufferedImage image2 = ImageIO.read(new File(uniqueTrackImagePaths.get(1)));
            BufferedImage image3 = ImageIO.read(new File(uniqueTrackImagePaths.get(2)));
            BufferedImage image4 = ImageIO.read(new File(uniqueTrackImagePaths.get(3)));

            int width = image1.getWidth() * 2;
            int height = image1.getHeight() * 2;
            combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = combined.createGraphics();
            g.drawImage(image1, 0, 0, null);
            g.drawImage(image2, image1.getWidth(), 0, null);
            g.drawImage(image3, 0, image1.getHeight(), null);
            g.drawImage(image4, image1.getWidth(), image1.getHeight(), null);
            g.dispose();
        }

        String playlistDir = uploadPlaylistDir + userId + File.separator + "playlist" + File.separator + playlistId;
        Path uploadPath = Paths.get(playlistDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String playlistImagePath = playlistDir + File.separator + "playlistImage.png";
        File outputfile = new File(playlistImagePath);
        ImageIO.write(combined, "PNG", outputfile);

        return playlistImagePath;
    }

    private List<String> getUniqueImagePaths(List<String> imagePaths) throws IOException, NoSuchAlgorithmException {
        Set<String> uniqueHashes = new HashSet<>();
        List<String> uniquePaths = new ArrayList<>();

        for (String path : imagePaths) {
            File file = new File(path);
            BufferedImage image = ImageIO.read(file);
            String hash = hashImage(image);

            if (!uniqueHashes.contains(hash)) {
                uniqueHashes.add(hash);
                uniquePaths.add(path);
            }

            if (uniquePaths.size() >= 4) {
                break;
            }
        }

        return uniquePaths;
    }

    private String hashImage(BufferedImage image) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(((DataBufferByte) image.getRaster().getDataBuffer()).getData());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}

