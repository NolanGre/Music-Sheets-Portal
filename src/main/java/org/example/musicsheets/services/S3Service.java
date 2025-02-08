package org.example.musicsheets.services;

import lombok.AllArgsConstructor;
import org.example.musicsheets.aws.AwsProperties;
import org.example.musicsheets.exceptions.FileDeleteException;
import org.example.musicsheets.exceptions.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.StorageClass;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

@Service
@AllArgsConstructor
public class S3Service implements FileService {

    private final S3Client s3Client;
    private final AwsProperties awsProperties;

    @Override
    public String uploadFile(String fileName, String fileFolder, MultipartFile multipartFile) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsProperties.getS3().getBucketName())
                    .key(fileFolder + "/" + fileName + getFileExtension(multipartFile))
                    .contentLength(multipartFile.getSize())
                    .storageClass(StorageClass.STANDARD)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(multipartFile.getInputStream().readAllBytes()));


            return String.format("https://%s.s3.%s.amazonaws.com/%s/%s",
                    awsProperties.getS3().getBucketName(),
                    awsProperties.getS3().getRegion(),
                    fileFolder,
                    fileName + getFileExtension(multipartFile));

        } catch (IOException e) {
            throw new FileUploadException("Failed to upload file: " + fileName);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            URL url = new URI(fileUrl).toURL();
            String bucketName = awsProperties.getS3().getBucketName();
            String path = url.getPath().substring(1);

            if (!url.getHost().contains(bucketName)) {
                throw new FileDeleteException("URL does not belong to our bucket!");
            }

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(path)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new FileDeleteException("Failed to delete file: " + fileUrl);
        }
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        throw new FileUploadException("Invalid file format: " + originalFilename);
    }

}
