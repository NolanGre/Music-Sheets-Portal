package org.example.musicsheets.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(String fileName, String fileFolder, MultipartFile file);

    void deleteFile(String fileUrl);
}
