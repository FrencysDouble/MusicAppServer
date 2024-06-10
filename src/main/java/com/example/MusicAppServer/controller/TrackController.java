package com.example.MusicAppServer.controller;


import com.example.MusicAppServer.service.ResponseService;
import com.example.MusicAppServer.service.TrackService;
import com.example.MusicAppServer.service.state.OperationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/track")
public class TrackController {

    private final TrackService trackService;

    private final ResponseService responseService;

    public TrackController(TrackService trackService, ResponseService responseService) {
        this.trackService = trackService;
        this.responseService = responseService;
    }

    @GetMapping("/getByName")
    public ResponseEntity getTrackByName(@RequestParam String name)
    {
        OperationResult status = trackService.getByName(name);
        return responseService.buildResponse(status.getStatus(),status.getListData());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getById(@PathVariable Long id)
    {
        OperationResult status = trackService.getById(id);
        return responseService.buildResponse(status.getStatus(),status.getData());
    }


}
