package com.example.MusicAppServer.controller;


import com.example.MusicAppServer.model.Artist;
import com.example.MusicAppServer.service.ArtistService;
import com.example.MusicAppServer.service.ResponseService;
import com.example.MusicAppServer.service.state.OperationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/artist")
public class ArtistController {
    private final ArtistService artistService;

    private final ResponseService responseService;

    public ArtistController(ArtistService artistService, ResponseService responseService) {
        this.artistService = artistService;
        this.responseService = responseService;
    }

    @PostMapping("/create")
    public ResponseEntity addArtist(@RequestParam("imageFile")MultipartFile image, @ModelAttribute Artist artist)
    {
        OperationResult<String> status = artistService.createArtist(artist,image);
        return responseService.buildResponse(status.getStatus() , status.getData());
    }

    @GetMapping("/getall")
    public ResponseEntity getAll()
    {
        OperationResult status = artistService.getAll();
        System.out.println(status.getListData().toString());
        return responseService.buildResponse(status.getStatus(), status.getListData());
    }
}
