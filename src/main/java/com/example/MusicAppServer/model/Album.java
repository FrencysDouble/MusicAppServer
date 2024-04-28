package com.example.MusicAppServer.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column
    private Long artist_id;

    @Column(name = "image_path")
    private String imagePath;

    @ElementCollection
    @CollectionTable(name = "album_track")
    private List<String> trackNames;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Track> tracks;
}
