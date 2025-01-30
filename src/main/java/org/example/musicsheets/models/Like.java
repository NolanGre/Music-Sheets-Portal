package org.example.musicsheets.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "LIKES")
@IdClass(Like.LikeId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHEET_ID", nullable = false)
    @NotNull(message = "Sheet cannot be null")
    private Sheet sheet;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull(message = "User cannot be null")
    private User user;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LikeId implements Serializable {
        private Sheet sheet;
        private User user;

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
            Like like = (Like) o;
            return (sheet != null && user != null) &&
                    Objects.equals(this.getSheet(), like.getSheet()) &&
                    Objects.equals(this.getUser(), like.getUser());
        }

        @Override
        public final int hashCode() {
            return this instanceof HibernateProxy
                    ? ((HibernateProxy) this).getHibernateLazyInitializer()
                    .getPersistentClass()
                    .hashCode()
                    : getClass().hashCode();
        }
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
        Like like = (Like) o;
        return (sheet != null && user != null) &&
                Objects.equals(this.getSheet(), like.getSheet()) &&
                Objects.equals(this.getUser(), like.getUser());


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
        return "Like{" +
                "sheet_id=" + sheet.getId() +
                ", user_id=" + user.getId() +
                '}';
    }
}
