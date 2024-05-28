package com.example.MusicAppServer.service;


import com.example.MusicAppServer.model.Track;
import com.example.MusicAppServer.repositories.TrackRepository;
import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    private final FileService fileService;

    private final TrackRepository trackRepository;

    public TrackService(FileService fileService, TrackRepository trackRepository) {
        this.fileService = fileService;
        this.trackRepository = trackRepository;
    }


    public OperationResult getByName(String name)
    {
        try {
            List<Track> tracks = trackRepository.findByNameContainingIgnoreCase(name);
            if (tracks == null)
            {
                return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
            }
            return new OperationResult<>(OperationStatus.SUCCESS,tracks);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
