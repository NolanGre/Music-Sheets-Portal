package org.example.musicsheets.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.musicsheets.validation.ValidEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "SHEETS")
public class Sheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Author cannot be blank")
    private String author;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User publisher;

    @Column(length = 1000)
    @Size(max = 1000, message = "Description text cannot exceed 1000 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @OneToMany(mappedBy = "sheet", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "sheet", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

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

    @Column(name = "FILE_URL", nullable = false)
    @NotNull(message = "File cannot be missing")
    private String fileUrl;  //TODO: make file services

    public Sheet(String title, String author, User publisher, String description, Genre genre, List<Like> likes, List<Comment> comments, String fileUrl) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.genre = genre;
        this.likes = likes;
        this.comments = comments;
        this.fileUrl = fileUrl;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer()
                .getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Sheet sheet = (Sheet) o;
        return getId() != null && Objects.equals(getId(), sheet.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                .hashCode()
                : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Sheet{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher+id=" + publisher.getId() +
                ", description='" + description + '\'' +
                ", genre=" + genre +
                '}';
    }
}
