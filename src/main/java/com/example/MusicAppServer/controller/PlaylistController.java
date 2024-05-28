package com.example.MusicAppServer.controller;


import com.example.MusicAppServer.model.Artist;
import com.example.MusicAppServer.model.Playlist;
import com.example.MusicAppServer.model.dto.PlaylistDTO;
import com.example.MusicAppServer.service.PlaylistService;
import com.example.MusicAppServer.service.ResponseService;
import com.example.MusicAppServer.service.state.OperationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    private final ResponseService responseService;

    public PlaylistController(PlaylistService playlistService, ResponseService responseService) {
        this.playlistService = playlistService;
        this.responseService = responseService;
    }

    @PostMapping("/create")
    public ResponseEntity createPlaylist(@RequestBody PlaylistDTO playlistDTO)
    {
        OperationResult status = playlistService.createPlaylist(playlistDTO);
        return responseService.buildResponse(status.getStatus() , status.getData());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Long id)
    {
        OperationResult status = playlistService.getById(id);
        return responseService.buildResponse(status.getStatus(),status.getData());
    }

    @GetMapping("/getAll")
    public ResponseEntity getAll()
    {
        OperationResult status = playlistService.getAll();
        return responseService.buildResponse(status.getStatus(),status.getListData());
    }

    @PostMapping("/addTrack")
    public ResponseEntity addTrack(@RequestParam Long playlistId,@RequestParam Long trackId)
    {
        OperationResult status = playlistService.addTrackToPlaylist(playlistId,trackId);
        return responseService.buildResponse(status.getStatus(),status.getListData());
    }
}
