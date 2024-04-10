package com.example.MusicAppServer.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    private static final String uploadDir = "C:\\Users\\Glebas\\IdeaProjects\\MusicAppServer\\upload";
    public void saveMusicFile(MultipartFile file) throws IOException {
        try {

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = uploadDir + File.separator + file.getOriginalFilename();
            File dest = new File(filePath);
            file.transferTo(dest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
