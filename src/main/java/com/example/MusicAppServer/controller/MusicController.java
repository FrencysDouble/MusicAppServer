package com.example.MusicAppServer.controller;

import com.example.MusicAppServer.service.MusicService;
import com.example.MusicAppServer.service.ResponseService;
import com.example.MusicAppServer.service.state.OperationResult;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

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
    public ResponseEntity uploadMusic(@RequestParam("file") MultipartFile file)
    {
        OperationResult<String> status = musicService.uploadMusic(file);
        return responseService.buildResponse(status.getStatus(),status.getData());
    }

    @GetMapping(value = "/stream/{filename}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> streamMusic(@PathVariable String filename)
    {
        try {
            Resource resource = musicService.streamMusic(filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

}
