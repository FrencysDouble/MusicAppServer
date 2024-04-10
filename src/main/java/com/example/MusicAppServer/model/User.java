package com.example.MusicAppServer.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "userName", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;
}
