package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Artist;
import com.example.MusicAppServer.repositories.ArtistRepository;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public OperationResult<String> createArtist(Artist artist)
    {
        try {
            artistRepository.save(artist);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR, null);
        }
        return new OperationResult<>(OperationStatus.SUCCESS, null);
    }

    public OperationResult <List<Artist>> getAll()
    {
        try {
            List<Artist> artistList = artistRepository.findAll();
            return new OperationResult<List<Artist>>(OperationStatus.SUCCESS, artistList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
