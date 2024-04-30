package com.example.MusicAppServer.controller;


import com.example.MusicAppServer.model.Album;
import com.example.MusicAppServer.model.Artist;
import com.example.MusicAppServer.service.AlbumService;
import com.example.MusicAppServer.service.ArtistService;
import com.example.MusicAppServer.service.ResponseService;
import com.example.MusicAppServer.service.state.OperationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/album")
public class AlbumController {

    private final AlbumService albumService;

    private final ResponseService responseService;

    public AlbumController(AlbumService albumService, ResponseService responseService) {
        this.albumService = albumService;
        this.responseService = responseService;
    }

    @PostMapping("/create")
    public ResponseEntity addAlbum(@RequestParam("artistId") Long artistId,
                                   @RequestParam("imageFile") MultipartFile image,
                                   @ModelAttribute Album album,
                                   @RequestParam("audioFiles[]") List<MultipartFile> audioFiles)
    {
        for (MultipartFile file : audioFiles) {
            System.out.println(file);
        }
        OperationResult status = albumService.createAlbum(artistId,album,image,audioFiles);
        return responseService.buildResponse(status.getStatus() , status.getData());
    }

    @GetMapping("/getall")
    public ResponseEntity getAll()
    {
        OperationResult status = albumService.getAllAlbums();
        System.out.println(status.getListData());
        return responseService.buildResponse(status.getStatus(),status.getListData());
    }

    @GetMapping("/getAlbumArtistBy/{id}")
    public ResponseEntity getAlbumArtistById(@PathVariable("id") Long id)
    {
        OperationResult status = albumService.getAllAlbumsByArtistId(id);
        return responseService.buildResponse(status.getStatus(),status.getListData());
    }

}
