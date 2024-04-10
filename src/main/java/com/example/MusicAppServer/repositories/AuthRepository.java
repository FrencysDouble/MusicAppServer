package com.example.MusicAppServer.repositories;

import com.example.MusicAppServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
