package com.nolan.musicsheetsportal.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}
