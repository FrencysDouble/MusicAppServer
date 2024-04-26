package com.example.MusicAppServer.service;


import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class MusicService {
    private final FileService fileService;

    public MusicService(FileService fileService) {
        this.fileService = fileService;
    }

    public OperationResult<String> uploadMusic(MultipartFile file)
    {
        if (file.isEmpty())
        {
            return new OperationResult<>(OperationStatus.INVALID_CREDENTIALS);
        }
        try {
            fileService.saveMusicFile(file);
            return new OperationResult<>(OperationStatus.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Resource streamMusic(String fileName) throws FileNotFoundException {
        File musicFile = fileService.getMusicFile(fileName);

        if (!musicFile.exists()) {
            throw new FileNotFoundException("Music file not found: " + fileName);
        }

        return new FileSystemResource(musicFile);
    }
}
