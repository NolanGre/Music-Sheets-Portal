package org.example.musicsheets.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.musicsheets.exceptions.*;
import org.example.musicsheets.models.User;
import org.example.musicsheets.models.UserRole;
import org.example.musicsheets.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SheetService sheetService;
    private final FileService fileService;
    private static final String FOLDER_NAME = "user-avatars";

    @Transactional
    public User createUser(User user, MultipartFile file) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new UserAlreadyExistsException("User with this login already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        User savedUser = userRepository.save(user);

        try {
            savedUser.setAvatarUrl(fileService.uploadFile(savedUser.getId().toString(), FOLDER_NAME, file));
            return userRepository.save(savedUser);
        } catch (Exception e) {
            throw new FileUploadException("Failed to upload file for user with ID: " + savedUser.getId() + "; " + e.getMessage());
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID: " + userId + " not found"));
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with login: " + login + " not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public User updateWholeUser(Long userId, User updatedUser, MultipartFile file) {
        User existingUser = getUserById(userId);
        updateUser(existingUser, updatedUser);

        try {
            if (existingUser.getAvatarUrl() != null) {
                fileService.deleteFile(existingUser.getAvatarUrl());
            }
            existingUser.setAvatarUrl(fileService.uploadFile(existingUser.getId().toString(), FOLDER_NAME, file));
            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new FileUpdateException("Failed to update file for user with ID: " + userId + "; " + e.getMessage());
        }
    }

    private void updateUser(User existingUser, User updatedUser) {
        existingUser.setLogin(updatedUser.getLogin());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setAvatarUrl(updatedUser.getAvatarUrl());
    }

    @Transactional
    public void deleteUser(Long userId) {
        User existingUser = getUserById(userId);
        String avatarUrl = existingUser.getAvatarUrl();

        sheetService.deleteAllSheetsByPublisherId(userId);
        userRepository.deleteById(userId);

        if (avatarUrl != null) {
            try {
                fileService.deleteFile(avatarUrl);
            } catch (Exception e) {
                throw new FileDeleteException("Failed to delete file for user with ID: " + userId + "; " + e.getMessage());
            }
        }
    }
}
