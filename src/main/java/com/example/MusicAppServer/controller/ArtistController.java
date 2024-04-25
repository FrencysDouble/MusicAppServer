package com.example.MusicAppServer.controller;


import com.example.MusicAppServer.model.Artist;
import com.example.MusicAppServer.service.ArtistService;
import com.example.MusicAppServer.service.ResponseService;
import com.example.MusicAppServer.service.state.OperationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity addArtist(@RequestBody Artist artist)
    {
        OperationResult<String> status = artistService.createArtist(artist);
        return responseService.buildResponse(status.getStatus() , status.getData());
    }

    @GetMapping("/getall")
    public ResponseEntity getAll()
    {
        OperationResult<List<Artist>> status = artistService.getAll();
        return responseService.buildResponse(status.getStatus(), status.getListData());
    }
}
