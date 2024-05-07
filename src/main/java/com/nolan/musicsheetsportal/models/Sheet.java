package com.nolan.musicsheetsportal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "SHEETS")
public class Sheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnore
    private MyUser author;

    @ElementCollection
    @CollectionTable(name = "sheet_genres", joinColumns = @JoinColumn(name = "sheet"))
    private List<String> genres;

    private String noteFileUrl;

    public Sheet(){}

    public Sheet(long id, String title, String description, MyUser author, List<String> genres, String noteFileUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.genres = genres;
        this.noteFileUrl = noteFileUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sheet sheet = (Sheet) o;
        return id == sheet.id && Objects.equals(title, sheet.title) && Objects.equals(description, sheet.description) && Objects.equals(author, sheet.author) && Objects.equals(genres, sheet.genres) && Objects.equals(noteFileUrl, sheet.noteFileUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, author, genres, noteFileUrl);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getNoteFileUrl() {
        return noteFileUrl;
    }

    public void setNoteFileUrl(String noteFileUrl) {
        this.noteFileUrl = noteFileUrl;
    }
}