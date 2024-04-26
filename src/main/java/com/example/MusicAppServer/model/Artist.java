package com.example.MusicAppServer.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name ="name")
    private String name;

    @Column(name = "image_path")
    private String imagePath;

    /*@OneToMany(mappedBy = "Album", cascade = CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();*/
}
