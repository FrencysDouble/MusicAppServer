package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Artist;
import com.example.MusicAppServer.repositories.ArtistRepository;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ArtistService {

    private final ArtistRepository artistRepository;


    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public OperationResult createArtist(Artist artist, MultipartFile image) {
        try {
            if (image != null && !image.isEmpty()) {
                artistRepository.save(artist);
                FileService fileService = new FileService(image,artist.getId());
                String filePath = fileService.saveArtistImage();
                artist.setImagePath(filePath);
            }
            artistRepository.save(artist);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
        return new OperationResult<>(OperationStatus.SUCCESS);
    }

    public OperationResult getAll() {
        try {
            List<Artist> artistList = artistRepository.findAll();
            if (artistList == null) {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            return new OperationResult<>(OperationStatus.SUCCESS, artistList);
        } catch (Exception e) {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public OperationResult getById(Long id) {
        try {
            Optional<Artist> artist = artistRepository.findById(id);
            if (artist == null) {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            return new OperationResult<>(OperationStatus.SUCCESS, artist);
        } catch (Exception e) {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public OperationResult getByName(String name)
    {
        try {
            List<Artist> artists = artistRepository.findByNameContainingIgnoreCase(name);
            System.out.println(artists.size());
            if (artists == null) {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            return new OperationResult<>(OperationStatus.SUCCESS, artists);
        } catch (Exception e) {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
