package org.example.musicsheets.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    public static final List<String> ALLOWED_CONTENT_TYPES = List.of("image/jpg", "image/jpeg", "image/png");
    public static final List<String> ALLOWED_EXTENSIONS = List.of(".jpg", ".jpeg", ".png");

    @Override
    public void initialize(ValidFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            addConstraintViolation(context, "No file uploaded.");
            return false;
        }

        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();

        if (!isAllowedContentType(contentType)) {
            addConstraintViolation(context, "Invalid content type: " + contentType);
            return false;
        }

        if (!isAllowedExtension(originalFilename)) {
            addConstraintViolation(context, "Invalid file extension. Only [.jpg .jpeg .png] are allowed.");
            return false;
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            addConstraintViolation(context, "File size exceeds the maximum allowed size of 5MB.");
            return false;
        }

        return true;
    }

    private boolean isAllowedContentType(String contentType) {
        return contentType != null && ALLOWED_CONTENT_TYPES.contains(contentType);
    }

    private boolean isAllowedExtension(String filename) {
        return filename != null && ALLOWED_EXTENSIONS.stream().anyMatch(filename.toLowerCase()::endsWith);
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
