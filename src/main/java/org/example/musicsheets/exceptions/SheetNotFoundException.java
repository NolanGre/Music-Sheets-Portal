package org.example.musicsheets.exceptions;

public class SheetNotFoundException extends RuntimeException {
    public SheetNotFoundException(String message) {
        super(message);
    }
}
