package com.example.MusicAppServer.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/v1/ui")
public class UserUiController {

    @GetMapping("/upload")
    public String getUi()
    {
        return "upload";
    }

    @GetMapping("/player")
    public String getPlayerUi()
    {
        return "musicPlayerTest";
    }

    @GetMapping("/artist")
    public String getArtistUi()
    {
        return "artistCreation";
    }

    @GetMapping()
    public String getAuthUi()
    {
        return "auth";
    }

    @GetMapping("/album")
    public String getAlbumUi()
    {
        return "albumCreation";
    }
    @GetMapping("admin")
    public String getAdminUi()
    {
        return "adminMainMenu";
    }
}
