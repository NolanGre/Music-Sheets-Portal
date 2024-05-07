package com.nolan.musicsheetsportal.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "USERS")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public MyUser(){}

    public MyUser(String username, String password, String email, String avatarUrl, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyUser myUser = (MyUser) o;
        return id == myUser.id && Objects.equals(username, myUser.username) && Objects.equals(password, myUser.password) && Objects.equals(email, myUser.email) && Objects.equals(avatarUrl, myUser.avatarUrl) && role == myUser.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, avatarUrl, role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
