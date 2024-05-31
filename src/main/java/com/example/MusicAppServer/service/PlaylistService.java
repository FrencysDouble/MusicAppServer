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
import jakarta.transaction.Transactional;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final AuthRepository authRepository;

    private final TrackRepository trackRepository;

    private final FileService fileService;

    public PlaylistService(PlaylistRepository playlistRepository, AuthRepository authRepository, TrackRepository trackRepository, FileService fileService) {
        this.playlistRepository = playlistRepository;
        this.authRepository = authRepository;
        this.trackRepository = trackRepository;
        this.fileService = fileService;
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

            playlist.setCreatorId(user.getId());
            playlist.setCreatorName(user.getUserName());
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


            Long userId = playlist.getCreatorId();


            List<String> trackImagePaths = playlist.getTracks().stream()
                    .map(Track::getImage_path)
                    .toList();


            String imagePath = fileService.generatePlaylistImage(trackImagePaths,playlistId.toString(),userId.toString());

            playlist.setImage_path(imagePath);

            playlistRepository.save(playlist);



            return new OperationResult(OperationStatus.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public OperationResult getAllByCreator(Long id)
    {
        try {
            List<Playlist> playlists = playlistRepository.findByCreatorId(id);
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
}
