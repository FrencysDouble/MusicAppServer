package com.example.MusicAppServer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "Track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name ="name")
    private String name;

    @Lob
    @Column(name = "image", columnDefinition="BLOB")
    private byte[] image;

    /*@ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;*/
}
