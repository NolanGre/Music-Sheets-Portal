package com.nolan.musicsheetsportal.models;

import java.io.Serializable;
import java.util.Objects;

public class LikeId implements Serializable {
    private Sheet sheet;
    private MyUser myUser;

    public LikeId() {
    }

    public LikeId(Sheet sheet, MyUser myUser) {
        this.sheet = sheet;
        this.myUser = myUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeId likeId = (LikeId) o;
        return Objects.equals(sheet, likeId.sheet) && Objects.equals(myUser, likeId.myUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sheet, myUser);
    }
}
