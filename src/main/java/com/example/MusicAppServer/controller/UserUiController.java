package com.example.MusicAppServer.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/v1/ui")
public class UserUiController {

    @GetMapping
    public String getUi()
    {
        return "upload";
    }

    @GetMapping("/player")
    public String getPLayer()
    {
        return "musicPlayerTest";
    }
}
