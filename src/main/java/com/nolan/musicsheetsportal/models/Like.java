package com.nolan.musicsheetsportal.models;

import jakarta.persistence.*;

@Entity
@Table(name = "LIKES")
@IdClass(LikeId.class)
public class Like {

    @Id
    @ManyToOne
    @JoinColumn(name = "SHEET_ID", nullable = false)
    private Sheet sheet;

    @Id
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private MyUser myUser;

    public Like(){}

    public Like(Sheet sheet, MyUser myUser) {
        this.sheet = sheet;
        this.myUser = myUser;
    }


    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }
}
