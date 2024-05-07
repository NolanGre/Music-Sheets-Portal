package com.nolan.musicsheetsportal.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String msg) {
        super(msg);
    }
}
