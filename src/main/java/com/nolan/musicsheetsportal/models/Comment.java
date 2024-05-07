package com.nolan.musicsheetsportal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "NOTE_ID", nullable = false)
    @JsonIgnore
    private Sheet sheet;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    @JsonIgnore
    private MyUser author;

    @Column(length = 1000)
    private String text;

    public Comment(){}

    public Comment(long id, Sheet sheet, MyUser author, String text) {
        this.id = id;
        this.sheet = sheet;
        this.author = author;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id && Objects.equals(sheet, comment.sheet) && Objects.equals(author, comment.author) && Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sheet, author, text);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
