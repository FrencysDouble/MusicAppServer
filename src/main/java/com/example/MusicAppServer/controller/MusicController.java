package com.example.MusicAppServer.controller;

import com.example.MusicAppServer.service.MusicService;
import com.example.MusicAppServer.service.ResponseService;
import com.example.MusicAppServer.service.state.OperationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/music")
public class MusicController {

    private final MusicService musicService;
    private final ResponseService responseService;

    public MusicController(MusicService musicService, ResponseService responseService) {
        this.musicService = musicService;
        this.responseService = responseService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMusic(@RequestParam("file") MultipartFile file)
    {
        OperationResult<String> status = musicService.uploadMusic(file);
        return responseService.buildResponse(status.getStatus(),status.getData());
    }
}
