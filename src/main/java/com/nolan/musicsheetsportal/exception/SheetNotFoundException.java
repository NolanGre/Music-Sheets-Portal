package com.nolan.musicsheetsportal.exception;

import com.nolan.musicsheetsportal.models.Sheet;

public class SheetNotFoundException extends RuntimeException {

    public SheetNotFoundException(String msg) {
        super(msg);
    }
}
