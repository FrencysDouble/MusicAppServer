package com.example.MusicAppServer.model;


import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "Playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "playlist_name")
    private String name;

    @Column(name = "creatorId")
    private Long creatorId;

    @Column(name = "creatorName")
    private String creatorName;

    @Column(name = "playlist_image_path")
    private String image_path;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "playlist_track",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private List<Track> tracks;


}
