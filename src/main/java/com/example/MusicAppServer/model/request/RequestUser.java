package com.example.MusicAppServer.model.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUser {
    private Long id;

    private String email;

    private String userName;

    private String password;
}
