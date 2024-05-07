package com.nolan.musicsheetsportal.repositories;

import com.nolan.musicsheetsportal.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUsername(String username);
}
