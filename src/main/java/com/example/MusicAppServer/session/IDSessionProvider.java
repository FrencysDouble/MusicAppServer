package com.example.MusicAppServer.session;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IDSessionProvider {
    public String getSessionId()
    {
        return UUID.randomUUID().toString();
    }
}
