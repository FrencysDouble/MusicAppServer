package com.example.MusicAppServer.service;


import com.example.MusicAppServer.service.state.OperationResult;
import com.example.MusicAppServer.service.state.OperationStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
            return new OperationResult<>(OperationStatus.INVALID_CREDENTIALS,null);
        }
        try {
            fileService.saveMusicFile(file);
            return new OperationResult<>(OperationStatus.SUCCESS,null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new OperationResult<>(OperationStatus.INTERNAL_SERVER_ERROR,null);
        }
    }
}
