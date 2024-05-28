package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Album;
import com.example.MusicAppServer.model.Playlist;
import com.example.MusicAppServer.model.Track;
import com.example.MusicAppServer.model.User;
import com.example.MusicAppServer.model.dto.PlaylistDTO;
import com.example.MusicAppServer.repositories.AuthRepository;
import com.example.MusicAppServer.repositories.PlaylistRepository;
import com.example.MusicAppServer.repositories.TrackRepository;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final AuthRepository authRepository;

    private final TrackRepository trackRepository;

    public PlaylistService(PlaylistRepository playlistRepository, AuthRepository authRepository, TrackRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.authRepository = authRepository;
        this.trackRepository = trackRepository;
    }

    public OperationResult createPlaylist(PlaylistDTO playlistDTO)
    {
        try {
            if (playlistDTO == null)
            {
                return new OperationResult<>(OperationStatus.INVALID_CREDENTIALS);
            }
            User user = authRepository.getById(playlistDTO.getCreatorId());
            if (user == null)
            {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            Playlist playlist = new Playlist();

            playlist.setCreator(user);
            playlist.setName(playlistDTO.getName());
            playlistRepository.save(playlist);

            return new OperationResult<>(OperationStatus.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public OperationResult getAll()
    {
        try {
            List<Playlist> playlists = playlistRepository.findAll();
            if (playlists == null)
            {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            return new OperationResult<>(OperationStatus.SUCCESS,playlists);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public OperationResult getById(Long id)
    {
        try {
            Optional<Playlist> playlist = playlistRepository.findById(id);
            if (playlist == null)
            {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            return new OperationResult<>(OperationStatus.SUCCESS,playlist);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public OperationResult addTrackToPlaylist(Long playlistId,Long trackId)
    {
        try{
            Playlist playlist = playlistRepository.getById(playlistId);
            if (playlist == null)
            {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            List<Track> playlistTracks = playlist.getTracks();
            if (playlistTracks == null)
            {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            Track track = trackRepository.getById(trackId);
            if (track == null)
            {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            playlistTracks.add(track);
            playlistRepository.save(playlist);
            return new OperationResult(OperationStatus.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
