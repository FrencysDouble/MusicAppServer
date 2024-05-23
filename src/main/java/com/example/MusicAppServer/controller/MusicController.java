package com.example.MusicAppServer.controller;

import com.example.MusicAppServer.repositories.TrackRepository;
import com.example.MusicAppServer.service.MusicService;
import com.example.MusicAppServer.service.ResponseService;
import com.example.MusicAppServer.service.state.OperationResult;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api/v1/music")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping(value = "/stream/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> streamMusic(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        try {
            File file = musicService.streamMusic(id);
            long fileLength = file.length();
            String range = headers.getFirst(HttpHeaders.RANGE);
            if (range == null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(fileLength)
                        .body(new FileSystemResource(file));
            }

            long rangeStart = 0;
            long rangeEnd = fileLength - 1;
            String[] ranges = range.replace("bytes=", "").split("-");
            try {
                rangeStart = Long.parseLong(ranges[0]);
                if (ranges.length > 1) {
                    rangeEnd = Long.parseLong(ranges[1]);
                }
            } catch (NumberFormatException ignored) {
            }

            if (rangeEnd > fileLength - 1) {
                rangeEnd = fileLength - 1;
            }

            long contentLength = rangeEnd - rangeStart + 1;
            InputStream inputStream = new FileInputStream(file);
            inputStream.skip(rangeStart);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength)
                    .body(new InputStreamResource(inputStream));
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
