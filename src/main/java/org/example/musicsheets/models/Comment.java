package org.example.musicsheets.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "COMMENTS")
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHEET_ID", nullable = false)
    @NotNull(message = "Sheet cannot be null")
    private Sheet sheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull(message = "Author cannot be null")
    private User author;

    @Column(length = 1000, nullable = false)   //TODO: front-end constrains for 1k symbols
    @Size(max = 1000, message = "Comment text cannot exceed 1000 characters")
    @NotBlank(message = "Comment text cannot be blank.")
    private String text;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATING_DATE")
    @PastOrPresent(message = "Creation date can be only past or present")
    private Date creatingDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFYING_DATE")
    @PastOrPresent(message = "Modification date can be only past or present")
    private Date modifyingDate;

    public Comment(Sheet sheet, User author, String text) {
        this.sheet = sheet;
        this.author = author;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", sheet_id=" + sheet.getId() +
                ", author_id=" + author.getId() +
                ", text='" + text + '\'' +
                '}';
    }
}
